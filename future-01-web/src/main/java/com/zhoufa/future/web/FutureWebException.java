package com.zhoufa.future.web;

/**
 * @author zhoufangan !
 * web异常
 */
public class FutureWebException extends RuntimeException {

    private int code;
    private String message;
    private Throwable exception;

    public FutureWebException(int code, String message, Throwable exception) {
        super(message, exception);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
