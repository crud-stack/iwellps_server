package com.iwell.eye.common.exception;

public class CommonCodeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -6974279506195439314L;

    private int statusCode;
    private String statusMessage;
    private String responseTime;
    private Object data;

    public CommonCodeNotFoundException(int statusCode, String statusMessage, Object data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    @Override
    public String getMessage() {
        return getStatusMessage();
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

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
