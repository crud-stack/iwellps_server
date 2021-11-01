package com.iwell.eye.common.exception.global;


import com.iwell.eye.common.util.DateUtil;

public class EyeRestControllerAdviceVO {

	private int statusCode;
	private String statusMessage;
	private String responseTime;
	private Object data;
	
	public EyeRestControllerAdviceVO(int statusCode, String statusMessage, Object data) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.data = data;
		this.responseTime = DateUtil.getCurrentTime();
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}	
	
}
