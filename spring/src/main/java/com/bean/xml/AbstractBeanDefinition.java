package com.bean.xml;

import com.bean.BeanDefinition;
import com.bean.MutablePropertyValues;

import java.lang.reflect.InvocationTargetException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/5 16:02
 */
public abstract class AbstractBeanDefinition implements BeanDefinition, Cloneable {

    private String aliasesName;
    private String name;
    private String id;
    private volatile Object beanClass;
    private boolean abstractFlag = false;
    private String scope = "";

    private MutablePropertyValues propertyValues;

    public AbstractBeanDefinition() {
        this(null);
    }

    public AbstractBeanDefinition(MutablePropertyValues pvs) {
        this.propertyValues = pvs;
    }

    public AbstractBeanDefinition(String id, String name, String aliasesName, MutablePropertyValues pvs) {
        this(pvs);
        setId(id);
        setName(name);
        setAliasesName(aliasesName);
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClass = beanClassName;
    }

    public void setBeanClass(Class<?> beanClass) {
        try {
            this.beanClass = beanClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBeanClassName() {
        Object beanClassObject = this.beanClass;
        if (beanClassObject instanceof Class) {
            return ((Class<?>) beanClassObject).getName();
        } else {
            return (String)beanClassObject;
        }
    }

    public void setAliasesName(String aliasesName) {
        this.aliasesName = aliasesName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<?> getBeanClass() throws IllegalStateException {
        Object beanClassObject = this.beanClass;
        if (beanClassObject == null) {
            throw new IllegalStateException("No bean class specified on bean definition");
        }
        if (!(beanClassObject instanceof Class)) {
            throw new IllegalStateException(
                    "Bean class name [" + beanClassObject + "] has not been resolved into an actual Class");
        }
        return (Class<?>) beanClassObject;
    }

    public boolean hasBeanClass() { return (this.beanClass instanceof Class); }

    @Override
    public void setScope(String scope) { this.scope = scope; }

    @Override
    public String getScope() { return this.scope; }

    public String getId() { return id; }

    public String getAliasesName() { return aliasesName; }

    public String getName() { return name; }

    public void setPropertyValues(MutablePropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public MutablePropertyValues getPropertyValues() {
        if (propertyValues == null) {
            propertyValues = new MutablePropertyValues();
        }
        return this.propertyValues;
    }

    @Override
    public boolean isSingleton() { return true; }

    @Override
    public Object getBean() {
        return this.beanClass;
    }

    @Override
    public boolean hasPropertyValues() {
        return (this.propertyValues != null && !this.propertyValues.isEmpty());
    }
}
