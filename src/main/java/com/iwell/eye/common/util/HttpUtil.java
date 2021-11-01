package com.iwell.eye.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.util.Enumeration;
import java.util.Locale;

public class HttpUtil {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static String getClientIP() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}

		return ip;
	}

	public static String getSessionId(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getSession().getId();
	}

	public static String getLocaleString() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return RequestContextUtils.getLocale(req).getLanguage();
	}

	public static Locale getLocale() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return RequestContextUtils.getLocale(req);
	}

	public static String getRefererURL(HttpServletRequest request, HttpServletResponse response) {
		String referrer = request.getHeader("referer");
		if (referrer == null || referrer.equals("")) {
			return request.getSession().getServletContext().getContextPath();
		}

		return referrer;
	}

	public static String getRequestURL(HttpServletRequest request) {
		return request.getRequestURL().toString();
	}

	public static String getRequestURL(ServletRequest request) {
		return getRequestURL((HttpServletRequest) request);
	}

	public static String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	public static String getRequestURI(ServletRequest request) {
		return getRequestURI((HttpServletRequest) request);
	}

	public static String getRequestMethod(HttpServletRequest request) {
		return request.getMethod();
	}

	public static String getRequestMethod(ServletRequest request) {
		return getRequestMethod((HttpServletRequest) request);
	}

	public static String getRequestQueryString(HttpServletRequest request) {
		return request.getQueryString();
	}

	public static String getRequestQueryString(ServletRequest request) {
		return getRequestQueryString((HttpServletRequest) request);
	}

	public static void printRequestParams(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			logger.debug("Header Name - " + headerName + ", Value - " + request.getHeader(headerName));
		}

		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			logger.debug("Parameter Name - " + paramName + ", Value - " + request.getParameter(paramName));
		}
	}

}
