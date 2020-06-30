package com.bean.impl;

import com.bean.BeanDefinition;
import com.bean.BeanMetadataElement;
import com.util.Assert;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/30 22:36
 */
public class BeanDefinitionHolder implements BeanMetadataElement {

    private final BeanDefinition beanDefinition;
    private final String beanName;

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
        Assert.notNull(beanDefinition, "BeanDefinition must not be null");
        Assert.notNull(beanName, "Bean name must not be null");
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public BeanDefinition getBeanDefinition() {
        return this.beanDefinition;
    }
}
