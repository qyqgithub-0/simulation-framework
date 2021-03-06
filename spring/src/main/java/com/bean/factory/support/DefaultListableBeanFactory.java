package com.bean.factory.support;

import com.bean.BeanDefinition;
import com.bean.BeanDefinitionRegistry;
import com.bean.BeanFactory;
import com.bean.ConfigurableListableBeanFactory;
import com.bean.impl.GenericBeanDefinition;
import com.bean.xml.AbstractBeanDefinition;
import com.exception.extension.BeansException;
import com.exception.extension.NoSuchBeanDefinitionException;
import com.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认工厂实现(单例模式，线程安全)，也是Spring的最终创建的工厂对象
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 16:01
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory,
        BeanDefinitionRegistry {

    private static volatile DefaultListableBeanFactory beanFactory;
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);
    private final Map<String, String> aliasesDefinitionMap = new ConcurrentHashMap<String, String>(256);
    private final Map<String, Class<?>> typeDefinitionMap = new ConcurrentHashMap<String, Class<?>>(256);
    private final Map<Class<?>, Object> resolvableDependencies = new ConcurrentHashMap<Class<?>, Object>(256);
    private volatile List<String> beanDefinitionNames = new ArrayList<String>(256);

    private volatile boolean configurationFrozen = false;

    private DefaultListableBeanFactory() { }

    @Override
    public Object getBean(String name) throws BeansException {
        Assert.notNull(name, "'beanName' must not be null");
        //根据name从beanDefinitionMap获取到BeanDefinition
        final BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        //从BeanDefinition中获取bean
        final Object bean = beanDefinition.getBean();
        if (!Objects.isNull(bean)) {
            return bean;
        }
        //没有对应的bean就抛出异常
        throw new NoSuchBeanDefinitionException("no bean named " + name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        Assert.notNull(requiredType, "'beanName' must not be null");
        return (T) resolvableDependencies.get(requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        Assert.notNull(name, "'beanName' must not be null");
        return beanDefinitionMap.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        Assert.notNull(name, "'beanName' must not be null");
        final BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        return beanDefinition.isSingleton();
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        Assert.notNull(name, "'beanName' must not be null");
        return true;
    }

    @Override
    public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        Assert.notNull(name, "'beanName' must not be null");
        Assert.notNull(typeToMatch, "typeToMatch must not be null");
        final Class<?> clazz = typeDefinitionMap.get(name);
        return typeToMatch == clazz;
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        Assert.notNull(name, "'beanName' must not be null");
        return typeDefinitionMap.get(name);
    }

    @Override
    public String getAliases(String name) {
        Assert.notNull(name, "'beanName' must not be null");
        return aliasesDefinitionMap.get(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        Assert.notNull(name, "'beanName' must not be null");
        register(((GenericBeanDefinition) beanDefinition).getId(), (AbstractBeanDefinition) beanDefinition);
    }

    /**
     * 注册bean
     * @param id id注册
     * @param beanDefinition 被注册的bean对应的BeanDefinition
     */
    private void register(String id, AbstractBeanDefinition beanDefinition) {
        final String name = beanDefinition.getBean().getClass().getName();
        Object oldBean;
        if (containsBean(name)) {
            oldBean = beanDefinitionMap.get(name);
            if (oldBean == beanDefinition) return;
        }
        //加入到beanDefinitionMap中
        beanDefinitionMap.put(id, beanDefinition);
        //根据BeanDefinition获取字节码
        final Class<?> clazz = beanDefinition.getBean().getClass();
        //将相应的数据添加进入对应的容器
        typeDefinitionMap.put(id, clazz);
        aliasesDefinitionMap.put(beanDefinition.getAliasesName(), id);
        resolvableDependencies.put(clazz, beanDefinition.getBean());
        beanDefinitionNames.add(id);
    }

    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        return beanDefinitionMap.get(beanName);
    }

    /**
     * 对外提供一个获取该工厂的接口
     * @return GenericBeanDefinition
     */
    public static DefaultListableBeanFactory getInstance() {
        if (beanFactory == null) {
            synchronized (GenericBeanDefinition.class) {
                if (beanFactory == null) {
                    beanFactory = new DefaultListableBeanFactory();
                }
            }
        }
        return beanFactory;
    }

    @Override
    public void freezeConfiguration() {
        this.configurationFrozen = true;
    }

    @Override
    public void preInstantiateSingleton() throws BeansException {
        List<String> beanNames = new ArrayList<String>(this.beanDefinitionNames);
        //寻找所有非懒加载的bean（便于简单，目前认为所有的bean都是singleton）
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.isSingleton()) {
                //进行处理
                super.getBean(beanName);
            }
        }
    }

    @Override
    protected BeanFactory getBeanFactory() throws BeansException {
        return this;
    }
}
