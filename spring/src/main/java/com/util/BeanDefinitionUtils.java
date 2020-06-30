package com.util;

import com.bean.BeanDefinitionRegister;
import com.bean.impl.BeanDefinitionHolder;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/30 22:31
 */
public abstract class BeanDefinitionUtils {

    /**
     * 将BeanDefinition注册到register里面
     * public class DefaultListableBeanDefinitionFactory implements
     *                      ConfigurableListableBeanFactory, BeanDefinitionRegister
     * @param definitionHolder
     * @param register
     */
    public static void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegister register) {
        String beanName = definitionHolder.getBeanName();
        register.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());
    }
}
