package com.exception.extension;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 22:57
 */
public class ApplicationContextException extends BeansException {

    public ApplicationContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationContextException(String message) {
        super(message);
    }
}
