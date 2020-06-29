package com.exception.extension;

import com.exception.BasicNestedRuntimeException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:44
 */
public class BeansException extends BasicNestedRuntimeException {

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
