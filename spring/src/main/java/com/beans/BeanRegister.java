package com.beans;

import com.beans.impl.DefaultBeanDefinition;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:56
 */
public interface BeanRegister {

    /**
     * bean注册
     * @param name
     * @param defaultBeanDefinition
     */
    void registerBeanDefinition(String name, DefaultBeanDefinition defaultBeanDefinition);

    /**
     * bean注册
     * @param defaultBeanDefinition
     */
    void registerBeanDefinition(DefaultBeanDefinition defaultBeanDefinition);

    /**
     * 通过Class注册
     * @param clazz
     */
    void registerBeanDefinition(Class<?> clazz);
}
