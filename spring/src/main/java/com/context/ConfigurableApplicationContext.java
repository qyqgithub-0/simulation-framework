package com.context;

import com.beans.BeanFactory;
import com.exception.extension.BeansException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 19:28
 */
public interface ConfigurableApplicationContext extends Lifecycle, ApplicationContext, Cloneable {

    /**
     * 获取BeanFactory的实现类
     * @return
     */
    BeanFactory getBeanFactory();

    /**
     * 加载或者刷新配置文件，配置文件也许是xml、properties或者其他的文件
     * @throws BeansException
     * @throws IllegalStateException
     */
    void refresh() throws BeansException, IllegalStateException;
}
