package com.bean;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:55
 */
public interface BeanDefinition {

    Object getBean();

    boolean isSingleton();

    void setBeanClassName(String beanClassName);

    String getBeanClassName();

    void setScope(String scope);

    String getScope();

    MutablePropertyValues getPropertyValues();

    default boolean hasPropertyValues() {
        return !getPropertyValues().isEmpty();
    }
}
