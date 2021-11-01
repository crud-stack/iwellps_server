package com.iwell.eye.common.exception;

import com.iwell.eye.common.constant.CommonConstant;
import org.apache.ibatis.binding.BindingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.util.NoSuchElementException;

public class CommonApiRestControllerAdvice {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@ExceptionHandler(CommonApiTimeoutException.class)
	@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
	public CommonApiRestControllerAdviceVO onCommonApiTimeoutException(HttpServletRequest req, CommonApiTimeoutException e) {
		logger.error("Detected onTimeout");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(e.getStatusCode(), e.getStatusMessage(), e.getData());
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public CommonApiRestControllerAdviceVO onAuthenticationException(HttpServletRequest request, AuthenticationException e) {
		logger.error("Detected onAuthenticationException");
		logger.error("\r\n", e);
		logger.error(e.getMessage());
		return new CommonApiRestControllerAdviceVO(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonApiRestControllerAdviceVO onAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
		logger.error("Detected onAccessDeniedException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(HttpStatus.FORBIDDEN.value(), e.getMessage(), null);
	}

	@ExceptionHandler(InvalidGrantException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonApiRestControllerAdviceVO OAuth2Exception(HttpServletRequest request, InvalidGrantException e) {
		logger.error("Detected InvalidGrantException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(HttpStatus.FORBIDDEN.value(), e.getMessage(), null);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public CommonApiRestControllerAdviceVO onNoHandlerFoundException(HttpServletRequest req, Exception e) {
		logger.error("Detected onNoHandlerFoundException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
	}

	@ExceptionHandler(CommonJsonException.class)
	@ResponseStatus(HttpStatus.OK)
	public CommonApiRestControllerAdviceVO onCommonJsonException(HttpServletRequest req, HttpServletResponse response, CommonJsonException e) {
		logger.error("Detected onCommonJsonException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(e.getStatusCode(), e.getStatusMessage(), e.getData());
	}

	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onDataAccessException(HttpServletRequest req, HttpServletResponse response, DataAccessException e) {
		logger.error("Detected onDataAccessException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(600, "데이터 처리 오류 발생", null);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onDuplicateKeyException(HttpServletRequest req, HttpServletResponse response, DuplicateKeyException e) {
		logger.error("Detected onDuplicateKeyException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(600, "데이터 처리 오류 발생", null);
	}
	
	@ExceptionHandler(BindingException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onBindingException(HttpServletRequest req, HttpServletResponse response, BindingException e) {
		logger.error("Detected onBindingException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(601, "데이터 처리 오류 발생", null);
	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onException(HttpServletRequest req, Exception e) {
		logger.error("Detected onException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal server error", null);
	}

	@ExceptionHandler(NoSuchFieldException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onException(HttpServletRequest req, NoSuchFieldException e) {
		logger.error("Detected NoSuchFieldException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(HttpStatus.NOT_FOUND.value(), "파일을 찾을 수 없습니다.", null);
	}

	//Json 양식에 문제가 있어 에러 발생시킴
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onHttpMessageNotReadableException(HttpServletRequest req, Exception e) {
		logger.error("Detected HttpMessageNotReadableException");
		logger.error("\r\n", e);

		return new CommonApiRestControllerAdviceVO(CommonConstant.ERR_JSON_ATTR_IS_INVALID, "Json Form Problem Check Please", null);
	}

	//Json 양식에 문제가 있어 에러 발생시킴
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonApiRestControllerAdviceVO onHttpRequestMethodNotSupportedException(HttpServletRequest req, Exception e) {
		logger.error("Detected HttpMessageNotReadableException");
		logger.error("\r\n", e);

		return new CommonApiRestControllerAdviceVO(HttpStatus.METHOD_NOT_ALLOWED.value(), "Request method '"+req.getMethod()+"' not supported", null);
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public CommonApiRestControllerAdviceVO onNoSuchElementException(HttpServletRequest req, Exception e) {
		logger.error("Detected onNoSuchElementException");
		logger.error("\r\n", e);

		return new CommonApiRestControllerAdviceVO(HttpStatus.NOT_FOUND.value(), "Not Found", null);
	}

	@ExceptionHandler(CommonCodeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public CommonApiRestControllerAdviceVO onCommonCodeNotFoundException(HttpServletRequest req, CommonCodeNotFoundException e) {
		logger.error("Detected CommonCodeNotFoundException");
		logger.error("\r\n", e);
		return new CommonApiRestControllerAdviceVO(CommonConstant.ERR_DATA_INTERNAL_CODE, "Not Found Code", null);
	}


}
