package com.context;

import com.bean.impl.DefaultListableBeanDefinitionFactory;
import com.bean.xml.XmlBeanDefinitionReader;
import com.exception.extension.BeansException;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 23:03
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

    /**
     * 通过XmlBeanDefinitionReader加载BeanDefinition
     * @param beanFactory
     * @throws BeansException
     * @throws IOException
     */
    @Override
    protected void loadBeanDefinition(DefaultListableBeanDefinitionFactory beanFactory) throws BeansException, IOException {
        //--------------------------------------
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        try {
            xmlBeanDefinitionReader.doLoadBeanDefinitions(getConfigLocations());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
