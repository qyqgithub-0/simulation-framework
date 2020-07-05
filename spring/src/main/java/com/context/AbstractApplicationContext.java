package com.context;

import com.bean.BeanFactory;
import com.bean.ConfigurableListableBeanFactory;
import com.exception.extension.BeansException;
import com.exception.extension.NoSuchBeanDefinitionException;

/**
 * 抽象类，实现了一部分方法
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 19:29
 */
public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

    private final Object startupShutdownMonitor = new Object();

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().isPrototype(name);
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        return getBeanFactory().isTypeMatch(name, typeToMatch);
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().getType(name);
    }

    @Override
    public String getAliases(String name) {
        return getBeanFactory().getAliases(name);
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    /**
     * 获取Bean工厂
     * @return
     * @throws IllegalStateException
     */
    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    /**
     * 刷新或者加载配置文件
     * @throws BeansException
     * @throws IllegalStateException
     */
    @Override
    public void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            prepareRefresh();
            //IoC重点，创建容器以及注册BeanDefinition，核心就是一个BeanDefinition的map
            ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
            //IoC重点，初始化所有singleton beans，懒加载的除外
            finishBeanFactoryInitialization(beanFactory);
            //广播事件，ApplicationContext初始化完成
            finishRefresh();
        }
    }

    protected void prepareRefresh() {

    }

    /**
     * 获取或者刷新Bean工厂
     * @return
     */
    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        //返回旧的BeanFactory(有的情况)，创建新的BeanFactory，加载Bean定义、注册Bean等等
        refreshBeanFactory();

        //返回刚刚创建的BeanFactory
        return getBeanFactory();
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.freezeConfiguration();
        beanFactory.preInstantiateSingleton();
    }

    protected void finishRefresh() {

    }

    protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;

    protected void destroyBeans() {
        //销毁
    }
}
