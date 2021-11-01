package com.iwell.eye.common.interceptor;

import com.iwell.eye.common.exception.CommonApiTimeoutException;
import com.iwell.eye.common.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class ApiHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	final protected static Logger logger = LoggerFactory.getLogger("http-logger");
	final private static String nextLine = "\r\n";

	@Value("${resttemplate.max.retry}")
	private int maxRetry;

	int tryCount = 0;

	@Autowired
	private SysLogService sysLogService;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
		ClientHttpResponse response = null;

		traceRequest(request, body);

		try {
			tryCount++;
			response = execution.execute(request, body);
		} catch (IOException e) {
			logger.error("● ApiHttpRequestInterceptor::IOException::Current tryCount::" + tryCount);
			logger.error("\r\n", e);

			if (tryCount >= maxRetry) {
				logger.warn("● ApiHttpRequestInterceptor::Shutting down the interceptor cuz Maximum settings reached ●");
				tryCount = 0;
				throw new CommonApiTimeoutException(504, e.getMessage(), null);
			} else {
				logger.warn("● ApiHttpRequestInterceptor::Retry until set rotation. ●");
				this.intercept(request, body, execution);
			}
		}

		traceResponse(response);

		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) {
		try {
			String out = "";
			String accessUri = "[" + request.getMethod() + "] " + request.getURI();
			String bodyString = new String(body, StandardCharsets.UTF_8);

			out = out + nextLine + "┌──────────────────────────────────────────────────";
			out = out + nextLine + "│ ● Http Client Request Interceptor ●";
			out = out + nextLine + "│ URI :: " + accessUri;
			out = out + nextLine + "│ Header :: " + request.getHeaders();
			out = out + nextLine + "│ Body :: " + bodyString;
			out = out + nextLine + "└──────────────────────────────────────────────────";

			logger.error(out);

			sysLogService.postSysLogApi(request, "Client:Request", bodyString);
		} catch (Exception e) {
			logger.error("CustomHttpRequestInterceptor :: traceRequest : Error", e);
		}
	}

	private void traceResponse(ClientHttpResponse response) {
		try {
			StringBuilder inputStringBuilder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
			String line = bufferedReader.readLine();
			while (line != null) {
				inputStringBuilder.append(line);
				inputStringBuilder.append('\n');
				line = bufferedReader.readLine();
			}

			String bodyString = inputStringBuilder.toString();

			String out = "";
			String status = "[" + response.getStatusCode() + "] " + response.getStatusText();
			out = out + nextLine + "┌──────────────────────────────────────────────────";
			out = out + nextLine + "│ ● Http Client Response Interceptor ●";
			out = out + nextLine + "│ Status :: " + status;
			out = out + nextLine + "│ Header :: " + response.getHeaders();
			out = out + nextLine + "│ Body :: " + bodyString;
			out = out + nextLine + "└──────────────────────────────────────────────────";

			logger.error(out);

			sysLogService.postSysLogApi(response, "Client:Response", bodyString);
		} catch (Exception e) {
			logger.error("CustomHttpRequestInterceptor :: traceResponse : Error", e);
		}
	}


}