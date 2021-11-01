package com.iwell.eye.common.tokener;

import com.iwell.eye.common.util.RestTemplateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * 
 * 
 */
@Data
@NoArgsConstructor
@Component
public class BAROGOTokener {
	private static final String BAROGO_USER_ID = "barogo";
	private static final String BAROGO_USER_PWD = "3A398384A6032B76737D47E354A7E998";
	private static final String BAROGO_AUTH_URL = "https://dev.stds.co.kr:8443/api/gorela/v1/oauth/token?grantType=rw";
	private static final String BAROGO_AUTH_REFRESH_URL = "https://dev.stds.co.kr:8443/api/gorela/v1/oauth/refresh";
	
	private String tokenType;
	private String accessToken;
	private String refreshToken;
	private String expireDT;
	
	private boolean getAuthentication() throws JsonMappingException, JsonProcessingException {
		String url = BAROGO_AUTH_URL;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(BAROGO_USER_ID, BAROGO_USER_PWD);
		
		HttpEntity<String> response = RestTemplateUtil.sendByBody(builder, HttpMethod.GET, new HttpEntity<String>(headers));
		
		HashMap<String, Object> authResponse = new ObjectMapper().readValue(response.getBody(), new TypeReference<HashMap<String, Object>>(){});
		
		if("200".equals(authResponse.get("statusCode").toString())) {
			HashMap<String, String> authMap = new ObjectMapper().convertValue(authResponse.get("data"), new TypeReference<HashMap<String, String>>(){});
			setTokenType(authMap.get("tokenType"));
			setAccessToken(authMap.get("accessToken"));
			setRefreshToken(authMap.get("refreshToken"));
			setExpireDT(authMap.get("expireDT"));
		} else {
			//현재 만기오류코드가 뭔지를 알수 없으므로 200 아니면 만기로 처리
			return false;
		}
		return true;
	}

	private void getRefreshAuth() throws JsonMappingException, JsonProcessingException {
		String url = BAROGO_AUTH_REFRESH_URL;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(getRefreshToken());
		
		HttpEntity<String> response = RestTemplateUtil.sendByBody(builder, HttpMethod.GET, new HttpEntity<String>(headers));
		
		HashMap<String, Object> authResponse = new ObjectMapper().readValue(response.getBody(), new TypeReference<HashMap<String, Object>>(){});
		
		if("200".equals(authResponse.get("statusCode").toString())) {
			HashMap<String, String> authMap = new ObjectMapper().convertValue(authResponse.get("data"), new TypeReference<HashMap<String, String>>(){});
			setTokenType(authMap.get("tokenType"));
			setAccessToken(authMap.get("accessToken"));
			setExpireDT(authMap.get("expireDT"));
		}
	}
	
	private boolean validateTokener() {
		if(getExpireDT() == null || getExpireDT() == "") return false;
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime expireDateTime = LocalDateTime.parse(getExpireDT(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		if(currentDateTime.isAfter(expireDateTime)) return false;
		
		return true;
	}
	
	public String processTokener() throws JsonMappingException, JsonProcessingException {
		if(!validateTokener()) if(!getAuthentication()) getRefreshAuth();
		
		return getAccessToken();
	}
}
