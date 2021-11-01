package com.iwell.eye.common.handler;

import com.iwell.eye.common.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApiAccessDeniedHandler implements AccessDeniedHandler {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		logger.warn("● AccessDeniedException Detection ●");
	    response.setStatus(HttpStatus.FORBIDDEN.value());
	    response.setHeader("Content-Type", "application/json; charset=UTF-8");
	    Map<String, Object> data = new HashMap<>();
	    data.put("statusCode", 403);
	    data.put("statusMessage", "[CRUDSYSTEM] Access Denied::" + accessDeniedException.getMessage());
	    data.put("responseTime", DateUtil.getCurrentTime());
	    data.put("data", "[" + request.getMethod() + "] " + request.getRequestURI().toString());	    

	    ObjectMapper mapper = new ObjectMapper();
	    String responseMsg = mapper.writeValueAsString(data);
	    response.getWriter().write(responseMsg);	    
	}

}
