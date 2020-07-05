package com.exception.extension;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/3 21:43
 */
public class BeanCreationException extends FatalBeanException {

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
