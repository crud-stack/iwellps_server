package com.iwell.eye.common.aop;

import com.iwell.eye.common.service.SysLogService;
import com.iwell.eye.common.util.HttpUtil;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ApiLogging {

	final protected static Logger logger = LoggerFactory.getLogger("http-logger");
	final private static String nextLine = "\r\n";

	@Autowired
	private SysLogService sysLogService;

	@Pointcut("within(com.iwell.eye.controller..*Controller)")
	public void onRequest() {}

	@Around("com.iwell.eye.common.aop.ApiLogging.onRequest()")
	public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String header = getHeader(request);
		String param = getPayload(pjp);
		long start = System.currentTimeMillis();
		try {
			return pjp.proceed(pjp.getArgs());
		} finally {
			long end = System.currentTimeMillis();
			printRunningInfo(request, pjp.getTarget().getClass().getSimpleName() + "." + pjp.getSignature().getName(), header, param, end - start);
		}
	}

	@Before("onRequest()")
	public void logBefore(JoinPoint joinPoint) {
		try {
			String out = "";
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			String header = getHeader(request);
			out = out + nextLine + "┌──────────────────────────────────────────────────";
			out = out + nextLine + "│ ● Server Request Received ●";
			out = out + nextLine + "│ RequestOn     :: [" + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "]";
			out = out + nextLine + "│ RequestHeader :: " + header;
			out = out + nextLine + "│ RequestBody   :: " + getJsonStringFromJoinPoint(joinPoint);
			out = out + nextLine + "└──────────────────────────────────────────────────";
			logger.info(out);
			sysLogService.postSysLogApi(request, "Server:Request", header, getJsonStringFromJoinPoint(joinPoint));
		} catch (Exception e) {
			logger.warn("AspectLogging :: logBefore : HasError", e);
		}

	}

	@AfterReturning(pointcut = "onRequest()", returning = "result")
	public void printReturnInfo(JoinPoint joinPoint, Object result) {
		try {
			if(result != null) {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				String out = "";
				out = out + nextLine + "┌──────────────────────────────────────────────────";
				out = out + nextLine + "│ ● Server Return Response ●";
				out = out + nextLine + "│ ResponseBy   :: [" + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "]";
				out = out + nextLine + "│ ResponseBody :: " + getJsonStringFromObject(result);
				out = out + nextLine + "└──────────────────────────────────────────────────";
				logger.info(out);
				sysLogService.postSysLogApi(request, "Server:Response", "", getJsonStringFromObject(result));
			}
		} catch (Exception e) {
			logger.warn("AspectLogging :: printReturnInfo : HasError", e);
		}
	}

	private String getHeader(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		JSONObject jsonObject = new JSONObject();

		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = request.getHeader(key);
			try {
				jsonObject.put(key, value);
			} catch (JSONException e) {
				logger.warn("AspectLogging :: getHeader : Error", e);
			}
		}
		return jsonObject.toString();
	}

	private String getPayload(ProceedingJoinPoint pjp) {
		CodeSignature signature = (CodeSignature) pjp.getSignature();
		JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < pjp.getArgs().length; i++) {
			String key = signature.getParameterNames()[i];
			Object value = pjp.getArgs()[i];
			try {
				jsonObject.put(key, value);
			} catch (JSONException e) {
				logger.warn("AspectLogging :: getPayload : Error", e);
			}
		}
		return jsonObject.toString();
	}

	private void printRunningInfo(HttpServletRequest request, String actor, String header, String param, long duration) {
		try {
			if(!actor.equals("LgOneIdClientController.getProfile")) {
				String out = "";
				String accessUser = HttpUtil.getClientIP();
				String accessUri = "[" + request.getMethod() + "] " + request.getRequestURI();
				out = out + nextLine + "┌──────────────────────────────────────────────────";
				out = out + nextLine + "│ Actor    :: [" + actor + "] Elapsed Time " + duration + "(ms). " + speedChater(duration);
				out = out + nextLine + "│ URI      :: " + accessUri;
				out = out + nextLine + "│ Accesser :: " + accessUser;
				out = out + nextLine + "│ Header   :: " + header;
				out = out + nextLine + "│ Payload  :: " + param;
				out = out + nextLine + "└──────────────────────────────────────────────────";
				if(duration> 3000) {
					logger.warn(out);
				}else {
					logger.info(out);
				}
			}
		} catch (Exception e) {
			logger.error("AspectLogging :: printRunningInfo : Error", e);
		}
	}

	private String speedChater(long duration) {
		String speed = "";
		if(duration <= 30) {
			speed = speed + "That's very fast.";
		}else if(duration <= 100) {
			speed = speed + "That's fast.";
		}else if(duration <= 500) {
			speed = speed + "More Quickly.";
		}else if(duration <= 1000) {
			speed = speed + "What's on?";
		}else if(duration <= 2000) {
			speed = speed + "That's slow.";
		}else if(duration <= 3000) {
			speed = speed + "That's very slow.";
		}else {
			speed = speed + "That's serious.";
		}
		speed = speed + "";
		return speed;
	}

	private String getJsonStringFromJoinPoint(JoinPoint joinPoint){
		Object[] objs = joinPoint.getArgs();
		String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i = 0; i < objs.length; i++) {
			if (!(objs[i] instanceof ExtendedServletRequestDataBinder) && !(objs[i] instanceof HttpServletResponseWrapper)) {
				paramMap.put(argNames[i], objs[i]);
			}
		}
		return (new Gson()).toJson(paramMap);
	}

	private String getJsonStringFromObject(Object object) {
		return (new Gson()).toJson(object);
	}

}
