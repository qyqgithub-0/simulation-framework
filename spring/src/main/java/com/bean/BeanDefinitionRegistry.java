package com.bean;

import com.bean.impl.GenericBeanDefinition;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:56
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition defaultBeanDefinition);
}
