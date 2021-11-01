package com.iwell.eye.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.invoke.MethodHandles;

@Component
public class RestTemplateUtil {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static RestOperations restOperations;


	@Autowired
	public void setRestOperations(RestOperations restOperations) {
		RestTemplateUtil.restOperations = restOperations;
	}

	public static HttpEntity<String> getDefaultHttpEntity() {
		return new HttpEntity<String>(getDefaultHttpHeader());
	}

	public static HttpEntity<String> getDefaultHttpEntity(String param) {
		return new HttpEntity<String>(param, getDefaultHttpHeader());
	}
	public static HttpEntity<String> getAuthenticationtHttpEntity(String param, String accessToken) {
		return new HttpEntity<String>(param, getAuthenticationHttpHeader(accessToken));
	}

	public static HttpEntity<String> getSejongHttpHeaderHttpEnitiy(String apiKey) {
		return new HttpEntity<String>(getSejongHttpHeader(apiKey));
	}

	public static HttpEntity<MultiValueMap<String, Object>> getSejongHttpHeaderHttpEnitiy(String apiKey, MediaType contentType, String phoneNumber, String token, MultiValueMap<String, Object> param) {
		return new HttpEntity<MultiValueMap<String, Object>>(param, getSejongHttpHeader(apiKey, contentType, phoneNumber, token));
	}
	public static HttpEntity<MultiValueMap<String, Object>> getSejongHttpHeaderHttpEnitiy(String apiKey, MediaType contentType, MultiValueMap<String, Object> param) {
		return new HttpEntity<MultiValueMap<String, Object>>(param, getSejongHttpHeader(apiKey, contentType));
	}

	public static HttpHeaders getSejongHttpHeader(String apiKey){
		HttpHeaders headers = new HttpHeaders();
		headers.set("sejongApiKey",apiKey);
		return headers;
	}
	public static HttpHeaders getSejongHttpHeader(String apiKey, MediaType contentType, String phoneNumber, String token){
		HttpHeaders headers = new HttpHeaders();
		headers.set("sejongApiKey",apiKey);
		headers.set("token",token);
		headers.set("phoneNumber",phoneNumber);
		headers.setContentType(contentType);
		return headers;
	}

	public static HttpHeaders getSejongHttpHeader(String apiKey, MediaType contentType){
		HttpHeaders headers = new HttpHeaders();
		headers.set("sejongApiKey",apiKey);
		headers.setContentType(contentType);
		return headers;
	}

	public static HttpHeaders getDefaultHttpHeader() {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}
	public static HttpHeaders getAuthenticationHttpHeader(String accessToken) {
		HttpHeaders headers = new HttpHeaders();

		headers.setBearerAuth(accessToken);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	// Body Base Request : POST, PUT, PATCH : HttpEntity Free
	public static HttpEntity<String> sendByBody(UriComponentsBuilder builder, HttpMethod httpMethod, HttpEntity<?> requestEntity) {
		return restOperations.exchange(builder.toUriString(), httpMethod, requestEntity, String.class);
	}
	// Body Base Request : POST, PUT, PATCH : HttpEntity Use Default
	public static HttpEntity<String> sendByBody(UriComponentsBuilder builder, HttpMethod httpMethod, String param) {
		return restOperations.exchange(builder.toUriString(), httpMethod, getDefaultHttpEntity(param), String.class);
	}

/*	// Body Base Request : POST, PUT, PATCH : HttpEntity Use Default
	public static HttpEntity<String> sejongSendByBody(UriComponents builder, HttpMethod httpMethod, String apiKey) {
		return restOperations.exchange(builder.toUriString(), httpMethod,getSejongHttpHeaderHttpEnitiy(apiKey), String.class);
	}*/

	//Body Base Request : POST, PUT, PATCH : HttpEntity Use Default
	public static HttpEntity<String> sejongSendByBody(UriComponentsBuilder builder, HttpMethod httpMethod, MediaType contentType, String apiKey, String phoneNumber, String token, MultiValueMap<String, Object> param) {
		return restOperations.exchange(builder.toUriString(), httpMethod, getSejongHttpHeaderHttpEnitiy(apiKey, contentType, phoneNumber, token, param), String.class);
	}

	// Body Base Request : POST, PUT, PATCH : HttpEntity Use Default
	public static HttpEntity<String> sejongSendByBody(UriComponentsBuilder builder, HttpMethod httpMethod, MediaType contentType, String apiKey, MultiValueMap<String, Object> param) {
		return restOperations.exchange(builder.toUriString(), httpMethod, getSejongHttpHeaderHttpEnitiy(apiKey, contentType, param), String.class);
	}

	public static HttpEntity<String> sendByBody(UriComponentsBuilder builder, HttpMethod httpMethod, String param, String accessToken) {
		return restOperations.exchange(builder.toUriString(), httpMethod, getAuthenticationtHttpEntity(param, accessToken), String.class);
	}
	// URI Base Request : GET, DELETE : HttpEntity Use Default
	public static HttpEntity<String> sendByUri(UriComponentsBuilder builder, HttpMethod httpMethod) {
		return restOperations.exchange(builder.toUriString(), httpMethod, getDefaultHttpEntity(), String.class);
	}
	// URI Base Request : GET, DELETE : HttpEntity Use Default
	public static HttpEntity<String> sejongSendByUri(UriComponents builder, HttpMethod httpMethod, String apiKey) {
		return restOperations.exchange(builder.toUriString(), httpMethod, getSejongHttpHeaderHttpEnitiy(apiKey), String.class);
	}
	// URI Base Request : GET, DELETE : HttpEntity Free
	public static HttpEntity<String> sendByUri(UriComponentsBuilder builder, HttpMethod httpMethod, HttpEntity<?> requestEntity) {
		return restOperations.exchange(builder.toUriString(), httpMethod, requestEntity, String.class);
	}
}

