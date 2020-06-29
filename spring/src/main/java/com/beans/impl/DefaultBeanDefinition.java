package com.beans.impl;

import com.beans.BeanDefinition;
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
@NoArgsConstructor
@AllArgsConstructor
public class DefaultBeanDefinition implements BeanDefinition {

    private String aliasesName;
    private Object bean;

    public DefaultBeanDefinition(Object bean) {
        this.bean = bean;
    }

    /**
     * 利用反射创建对应的bean
     * @param aliasesName 别名
     * @param clazz 字节码
     */
    public DefaultBeanDefinition(String aliasesName, Class clazz) {
        this.aliasesName = aliasesName;
        try {
            final Object obj = clazz.getDeclaredConstructor().newInstance();
            this.bean = obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化BeanDefinition
     * @param clazz 字节码
     * @return 返回一个DefaultBeanDefinition
     */
    public static DefaultBeanDefinition initBeanDefinition(Class<?> clazz) {
        try {
            final Object obj = clazz.getDeclaredConstructor().newInstance();
            return new DefaultBeanDefinition(obj);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeansException(e.getMessage());
        }
    }

    @Override
    public Object getBean() {
        return this.bean;
    }

    @Override
    public String getAliasesName() {
        if (aliasesName == null || aliasesName.equals("")) {
            return bean.getClass().getName();
        }
        return aliasesName;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
