package com.iwell.eye.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	final protected static String nextLine = "\r\n";
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() != HttpStatus.OK) {
    		logger.warn("┌──────────────────────────────");
    		logger.warn("│ RestTemplateErrorHandler getStatusCode() hasError! ");
    		logger.warn("└──────────────────────────────");
    		String trace = nextLine;
    		trace = trace + nextLine + "getStatusCode: " + response.getStatusCode();
    		trace = trace + nextLine + "getStatusText: " + response.getStatusText();
    		trace = trace + nextLine + "getHeaders: " + response.getHeaders();
    		trace = trace + nextLine + "getBody: " + response.getStatusCode();
    		logger.warn(trace);
    		/*
            if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
                logger.debug("Call returned a error 403 forbidden resposne ");
                return true;
            }
            */
        }
        return false;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		logger.error("┌──────────────────────────────");
		logger.error("│ RestTemplateErrorHandler handleError! ");
		logger.error("└──────────────────────────────");
	}

}
