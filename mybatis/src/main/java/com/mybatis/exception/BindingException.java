package com.mybatis.exception;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/8 18:33
 */
public class BindingException extends RuntimeException {

    private static final long serialVersionUID = 4300802238789381562L;

    public BindingException() {
        super();
    }

    public BindingException(String message) {
        super(message);
    }

    public BindingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingException(Throwable cause) {
        super(cause);
    }
}
