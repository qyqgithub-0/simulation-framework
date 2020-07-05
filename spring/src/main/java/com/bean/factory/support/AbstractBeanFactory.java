package com.bean.factory.support;

import com.bean.BeanFactory;
import com.bean.ConfigurableListableBeanFactory;
import com.exception.extension.BeansException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/1 22:10
 */
public abstract class AbstractBeanFactory implements ConfigurableListableBeanFactory {

    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null, null, false);
    }

    protected <T> T doGetBean(final String name, final Class<T> requireType, final Object[] args
            , boolean typeCheckOnly) throws BeansException {

        Object bean = null;
        //获取bean容器，本质是DefaultListableBeanFactory，我们将会从里面得到BeanDefinition，根据其信息创建实例并且加入
        BeanFactory beanFactory = getBeanFactory();
        if (beanFactory instanceof DefaultListableBeanFactory) {
            bean = createBean(name, args);
        }
        return (T) bean;
    }

    protected abstract Object createBean(String beanName, Object[] args) throws BeansException;

    protected abstract BeanFactory getBeanFactory() throws BeansException;
}
