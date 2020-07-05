package com.util;

import com.bean.BeanDefinitionRegistry;
import com.bean.impl.BeanDefinitionHolder;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/30 22:31
 */
public abstract class BeanDefinitionUtils {

    /**
     * 将BeanDefinition注册到register里面
     * public class DefaultListableBeanFactory implements
     *                      ConfigurableListableBeanFactory, BeanDefinitionRegistry
     * @param definitionHolder
     * @param register
     */
    public static void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry register) {
        String beanName = definitionHolder.getBeanName();
        register.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());
    }
}
