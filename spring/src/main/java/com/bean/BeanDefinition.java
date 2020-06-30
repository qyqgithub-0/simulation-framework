package com.bean;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 15:55
 */
public interface BeanDefinition {

    /**
     * 获取bean
     * @return
     */
    Object getBean();

    /**
     * 获取别名
     * @return
     */
    String getAliasesName();

    /**
     * 是否是单例
     * @return
     */
    boolean isSingleton();
}
