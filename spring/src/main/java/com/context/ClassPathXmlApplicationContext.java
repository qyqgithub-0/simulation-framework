package com.context;

import com.bean.ConfigurableListableBeanFactory;
import com.exception.extension.BeansException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 19:32
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        //设置配置文件的路径(AbstractRefreshableConfigApplicationContext)
        setConfigLocation(configLocation);
        refresh();
    }
}
