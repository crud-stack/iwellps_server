package com.iwell.eye.common.exception;

public class RestException extends RuntimeException {

    private static final long serialVersionUID = 6495921485443408475L;

    private int code;
    private String message;

    public RestException(int code, String message) {
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
