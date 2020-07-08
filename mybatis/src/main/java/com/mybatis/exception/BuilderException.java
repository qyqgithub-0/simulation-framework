package com.mybatis.exception;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 12:28
 */
public class BuilderException extends RuntimeException {

    public BuilderException() {
        super();
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable e) {
        super(e);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
