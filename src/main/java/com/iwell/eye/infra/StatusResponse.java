package com.iwell.eye.infra;

import com.iwell.eye.common.constant.CommonConstant;
import com.iwell.eye.common.util.DateUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author KMG
 * 중계 상태응답 클래스
 */
@Data
public class StatusResponse<T> implements Serializable {
	
	private static final long serialVersionUID = 8156722967504389944L;
	private int statusCode;
	private String statusMessage;
	private String responseTime;
	private T data;
	
	
	public StatusResponse(int statusCode, String statusMessage) {
		setData(statusCode, statusMessage, null);
	}
	
	public StatusResponse(int statusCode, String statusMessage, T data) {
		setData(statusCode, statusMessage, data);
	}
	
	public StatusResponse(T data) {
		/* 정상응답 가정 초기값 set */
		this.statusCode = CommonConstant.ERR_SUCCESS;
		this.statusMessage = "success";
		this.responseTime = DateUtil.getCurrentTime();
		this.data = data;
	}
	
	public StatusResponse() {
		/* 정상응답 가정 초기값 set */
		this.statusCode = CommonConstant.ERR_SUCCESS;
		this.statusMessage = "success";
		this.responseTime = DateUtil.getCurrentTime();
		this.data = null;
	}
	
	public void setData(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.responseTime = DateUtil.getCurrentTime();
		this.data = null;
	}
	
	public void setData(int statusCode, String statusMessage, T data) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.responseTime = DateUtil.getCurrentTime();
		this.data = data;
	}
}
