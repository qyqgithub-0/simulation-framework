package com.bean;

import com.exception.extension.BeansException;
import com.exception.extension.NoSuchBeanDefinitionException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:31
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;

    boolean containsBean(String name);

    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

    boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException;

    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

    String getAliases(String name);
}
