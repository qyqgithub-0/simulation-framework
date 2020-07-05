package com.bean.xml;

import com.bean.BeanDefinitionRegistry;
import com.bean.MutablePropertyValues;
import com.bean.PropertyValue;
import com.bean.impl.BeanDefinitionHolder;
import com.bean.impl.GenericBeanDefinition;
import com.util.BeanDefinitionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/30 8:00
 */
public class XmlBeanDefinitionReader {

    private final BeanDefinitionRegistry register;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry register) {
        this.register = register;
    }

    /**
     * 传入一个配置文件的位置，解析bean标签将其信息存在BeanDefinition，并且存入BeanFactory中
     * @param fileLocation 配置文件路径
     * @return bean的数量
     */
    public int doLoadBeanDefinitions(String fileLocation) throws DocumentException {
        try {
            //加载配置文件的Document
            Document doc = doLoadDocument(new File(fileLocation));
            return registerBeanDefinitions(doc);
        } catch (DocumentException e) {
            throw e;
        }
    }

    protected Document doLoadDocument(File file) throws DocumentException {
        SAXReader reader =  new SAXReader();
        return reader.read(file);
    }

    /**
     * 返回当前配置文件中加载了多少数量的bean
     * @param doc
     * @return
     */
    public int registerBeanDefinitions(Document doc) {
        int count = 0;
        //获取根节点
        Element root = doc.getRootElement();
        //获取所有的bean标签
        List<Element> elements = root.elements("bean");
        for (Element element : elements) {
            //获取class属性和id属性
            Class<?> clazz = null;
            try {
                clazz = Class.forName(element.attributeValue("class"));
            } catch (ClassNotFoundException e) {
                e.getCause();
            }
            String id = element.attributeValue("id");
            String name = element.attributeValue("name");
            String alias = element.attributeValue("alias");

            MutablePropertyValues propertyValues = new MutablePropertyValues();
            //默认就是singleton
//            String scope = element.attributeValue("scope") == null ? "singleton" : element.attributeValue("scope");
            System.out.println("---------------bean标签----------------");
            //读取bean标签下的property标签
            List<Element> propertyList = element.elements("property");
            for (Element property : propertyList) {
                String propName = property.attributeValue("name");
                String value = property.attributeValue("value");
                try {
                    Integer intValue = Integer.valueOf(value);
                    propertyValues.addPropertyValue(new PropertyValue(propName, intValue));
                } catch (NumberFormatException e) {
                    propertyValues.addPropertyValue(new PropertyValue(propName, value));
                }
            }

            GenericBeanDefinition beanDefinition = new GenericBeanDefinition(id, name, alias, propertyValues);
            beanDefinition.setBeanClass(clazz);
            BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, id);

            BeanDefinitionUtils.registerBeanDefinition(holder, register);
            count++;
        }
        return count;
    }
}
