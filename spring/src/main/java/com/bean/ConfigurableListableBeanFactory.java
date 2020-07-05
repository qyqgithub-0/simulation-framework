package com.bean;

import com.exception.extension.BeansException;

/**
 * 在Spring框架中，ConfigurableListableBeanFactory继承了
 * ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory，
 * 为了简洁，就直接继承BeanFactory
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 22:23
 */
public interface ConfigurableListableBeanFactory extends BeanFactory {

    void freezeConfiguration();

    void preInstantiateSingleton() throws BeansException;
}
