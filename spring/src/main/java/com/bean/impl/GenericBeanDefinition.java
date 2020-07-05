package com.bean.impl;

import com.bean.BeanDefinition;
import com.bean.MutablePropertyValues;
import com.bean.xml.AbstractBeanDefinition;
import com.exception.extension.BeansException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;

/**
 * Bean对应的BeanDefinition，包含了Bean的一些结构信息
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:57
 */
public class GenericBeanDefinition extends AbstractBeanDefinition {

    public GenericBeanDefinition() {
        super();
    }

    public GenericBeanDefinition(String id, String name, String aliasesName, MutablePropertyValues pvs) {
        super(id, name, aliasesName, pvs);
    }
}
