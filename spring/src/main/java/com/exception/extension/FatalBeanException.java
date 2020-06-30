package com.exception.extension;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/30 22:32
 */
public class FatalBeanException extends BeansException {

    public FatalBeanException(String message) {
        super(message);
    }

    public FatalBeanException(String message, Throwable cause) {
        super(message, cause);
    }
}
