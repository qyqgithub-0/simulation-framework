package com.exception.extension;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/30 22:33
 */
public class BeanDefinitionStoreException extends FatalBeanException {
    public BeanDefinitionStoreException(String message) {
        super(message);
    }

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
