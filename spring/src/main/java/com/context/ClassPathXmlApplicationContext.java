package com.context;

import com.beans.BeanFactory;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 19:32
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext() {
        refresh();
    }

    @Override
    public BeanFactory getBeanFactory() throws IllegalStateException {
        return null;
    }
}
