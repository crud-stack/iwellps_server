package com.iwell.eye.common.exception;

public class RestTimeoutException extends RestException{

    private static final long serialVersionUID = -2275211751796815415L;
    private int code;
    private String message;

    public RestTimeoutException(int code, String message) {
        super(code, message);

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
