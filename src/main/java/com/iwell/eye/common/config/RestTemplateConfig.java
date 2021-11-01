package com.iwell.eye.common.config;

import com.iwell.eye.common.handler.RestTemplateErrorHandler;
import com.iwell.eye.common.interceptor.ApiHttpRequestInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@Configuration
public class RestTemplateConfig {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Value("${resttemplate.connection.timeout}")
	private int connectionTimeout;

	@Value("${resttemplate.read.timeout}")
	private int readTimeout;

	@Value("${resttemplate.max-connections}")
	private int maxConnections;

	public RestTemplateConfig() {
		System.out.println("┌──────────────────────────────");
		System.out.println("│ Doing RestTemplateConfig ...");
		System.out.println("└──────────────────────────────");
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		logger.info("maxConnections: " + maxConnections);
		logger.info("connectionTimeout: " + connectionTimeout);
		logger.info("readTimeout: " + readTimeout);

		CloseableHttpClient httpClient = HttpClientBuilder.create() //
				.setMaxConnTotal(maxConnections) // 최대 커넥션 갯수
				.setMaxConnPerRoute(maxConnections / 3) // IP/domain name당 최대 커넥션 갯수
				.evictExpiredConnections() //
				.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		factory.setConnectTimeout(connectionTimeout);
		factory.setReadTimeout(readTimeout);

		return factory;
	}

	@Bean
	public RestOperations restTemplate() {
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(this.clientHttpRequestFactory()));

		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		restTemplate.setErrorHandler(new RestTemplateErrorHandler());

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(apiHttpRequestInterceptor());

		restTemplate.setInterceptors(interceptors);

		return restTemplate;
	}

	@Bean
	public ApiHttpRequestInterceptor apiHttpRequestInterceptor() {
		return new ApiHttpRequestInterceptor();
	}

}
