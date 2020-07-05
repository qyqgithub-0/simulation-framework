package com.context;

import com.bean.ConfigurableListableBeanFactory;
import com.bean.factory.support.DefaultListableBeanFactory;
import com.exception.extension.ApplicationContextException;
import com.exception.extension.BeansException;

import java.io.IOException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 22:10
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private final Object beanFactoryMonitor = new Object();
    private DefaultListableBeanFactory beanFactory;

    /**
     * 当前是否已经存在了一个BeanFactory
     * @return
     */
    protected final boolean hasBeanFactory() {
        synchronized (this.beanFactoryMonitor) {
            return (this.beanFactory != null);
        }
    }

    /**
     * 创建一个bean工厂
     * @return 工厂实例
     */
    protected DefaultListableBeanFactory createBeanFactory() {
        return DefaultListableBeanFactory.getInstance();
    }

    /**
     * 获取BeanFactory，如果为null则抛出异常
     * @return
     * @throws IllegalStateException
     */
    @Override
    public final ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        synchronized (this.beanFactoryMonitor) {
            if (this.beanFactory == null) {
                throw new IllegalStateException("BeanFactory not initialized or already closed - " +
                        "call 'refresh' before accessing beans via the ApplicationContext");
            }
        }
        return this.beanFactory;
    }

    /**
     * 这里创建了BeanFactory并且将BeanDefinition加载到了容器
     * @throws BeansException
     * @throws IllegalStateException
     */
    @Override
    protected void refreshBeanFactory() throws BeansException, IllegalStateException {
        if (hasBeanFactory()) {
            destroyBeans();
        }
        try {
            //初始化一个DefaultListableBeanFactory
            DefaultListableBeanFactory beanFactory = createBeanFactory();
            //下面是很重要的两个方法，是否允许覆盖
            customizeBeanFactory(beanFactory);
            //加载BeanDefinition到BeanFactory
            loadBeanDefinition(beanFactory);

            //容器创建完毕
            synchronized (this.beanFactoryMonitor) {
                this.beanFactory = beanFactory;
            }
        } catch (IOException e) {
            throw new ApplicationContextException(e.getMessage());
        }
    }

    /**
     * 是否允许循环覆盖，是否允许循环引用
     * @param beanFactory
     */
    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {

    }

    /**
     * 加载一个BeanDefinition到BeanFactory中
     * @param beanFactory
     * @throws BeansException
     * @throws IOException
     */
    protected abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory) throws BeansException, IOException;
}
