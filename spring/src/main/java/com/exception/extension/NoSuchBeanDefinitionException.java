package com.exception.extension;

import com.exception.BasicNestedRuntimeException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:45
 */
public class NoSuchBeanDefinitionException extends BasicNestedRuntimeException {

    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }

    public NoSuchBeanDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }
}
