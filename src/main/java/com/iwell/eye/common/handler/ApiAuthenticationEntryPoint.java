package com.iwell.eye.common.handler;

import com.iwell.eye.common.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/*
 * @author KMG
 * Oauth2.0 자격증명 어플리케이션 Basic Auth username 및 password 틀릴경우 호출
 */
@Component
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		logger.warn("● ApiAuthenticationEntryPoint Detection ●");
	    response.setStatus(HttpStatus.UNAUTHORIZED.value());
	    response.setHeader("Content-Type", "application/json; charset=UTF-8");
	    Map<String, Object> data = new HashMap<>();
	    data.put("statusCode", 401);
	    data.put("statusMessage", "[CRUDSYSTEM] Require Certification::" + authException.getMessage());
	    data.put("responseTime", DateUtil.getCurrentTime());
	    data.put("data", "[" + request.getMethod() + "] " + request.getRequestURI().toString());		    
	    
	    ObjectMapper mapper = new ObjectMapper();
	    String responseMsg = mapper.writeValueAsString(data);
	    response.getWriter().write(responseMsg);
	}

}
