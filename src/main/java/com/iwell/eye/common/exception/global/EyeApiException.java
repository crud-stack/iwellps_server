package com.iwell.eye.common.exception.global;


public class EyeApiException extends RuntimeException {

	private static final long serialVersionUID = 9158696157857638316L;

	private int code;
	private String message;

	public EyeApiException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
