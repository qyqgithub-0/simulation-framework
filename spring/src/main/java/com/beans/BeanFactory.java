package com.beans;

import com.exception.extension.BeansException;
import com.exception.extension.NoSuchBeanDefinitionException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:31
 */
public interface BeanFactory {

    /**
     * 获取bean
     * @param name
     * @return
     * @throws BeansException
     */
    Object getBean(String name) throws BeansException;

    /**
     * 根据类型获取bean
     * @param requiredType bean的字节码
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 是否包含一个bean
     * @param name bean的名字
     * @return 是否包含
     */
    boolean containsBean(String name);

    /**
     * 是否是单例
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    /**
     * 是否是原型
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

    /**
     * 类型是否匹配
     * @param name
     * @param typeToMatch
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException;

    /**
     * 获取类型
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

    /**
     * 获取别名
     * @param name
     * @return
     */
    String getAliases(String name);
}
