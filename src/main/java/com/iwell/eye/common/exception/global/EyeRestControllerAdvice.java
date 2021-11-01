package com.iwell.eye.common.exception.global;

import com.iwell.eye.common.exception.CommonApiRestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;


@RestControllerAdvice
public class EyeRestControllerAdvice extends CommonApiRestControllerAdvice {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@ExceptionHandler(EyeException.class)
	@ResponseStatus(HttpStatus.OK)
	public EyeRestControllerAdviceVO onApiException(HttpServletRequest req, EyeException e) {
		logger.error("Detected ConsoleException");
		logger.error("\r\n", e);
		return new EyeRestControllerAdviceVO(e.getStatusCode(), e.getStatusMessage(), e.getData());
	}

	@ExceptionHandler(EyeApiException.class)
	@ResponseStatus(HttpStatus.OK)
	public EyeRestControllerAdviceVO onApiException(HttpServletRequest req, EyeApiException e) {
		logger.error("AuthorizeApiException: ", e);
		return new EyeRestControllerAdviceVO(e.getCode(), e.getMessage(), null);
	}
}
