package com.bean.xml;

import com.bean.BeanDefinitionRegister;
import com.bean.impl.BeanDefinitionHolder;
import com.bean.impl.DefaultBeanDefinition;
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

    private final BeanDefinitionRegister register;

    public XmlBeanDefinitionReader(BeanDefinitionRegister register) {
        this.register = register;
    }

    /**
     * 传入一个配置文件的位置，解析bean标签将其信息存在BeanDefinition，并且存入BeanFactory中
     * @param fileLocation
     * @return
     */
    public int doLoadBeanDefinitions(String fileLocation) throws DocumentException {
        try {
            //加载配置文件的Document
            Document doc = doLoadDocument(new File(fileLocation));
            int count = registerBeanDefinitions(doc);
            return count;
        } catch (DocumentException e) {
            throw e;
        }
    }

    /**
     * 获取配置文件的Document对象
     * @param file 配置文件
     * @return
     * @throws DocumentException
     */
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
        List<Element> elements = root.elements("bean");
        for (Element element : elements) {
            //获取class属性和id属性
            Class<?> clazz = null;
            try {
                clazz = Class.forName(element.attributeValue("class"));
            } catch (ClassNotFoundException e) {
                e.getCause();
            }
//            String classAttr = element.attributeValue("class");
            String id = element.attributeValue("id");
            //默认就是singleton
//            String scope = element.attributeValue("scope") == null ? "singleton" : element.attributeValue("scope");
//            System.out.println("---------------bean标签----------------");
//            System.out.println("class属性：" + classAttr);
//            System.out.println("id属性：" + id);
//            System.out.println("scope属性：" + scope);
//
//            //读取bean标签下的property标签
//            List<Element> propertyList = element.elements("property");
//            for (Element property : propertyList) {
//                String name = property.attributeValue("name");
//                String value = property.attributeValue("value");
//                System.out.println("property的name属性：" + name);
//                System.out.println("property的value属性：" + value);
//            }
//            System.out.println("--------------------------------------");

            DefaultBeanDefinition beanDefinition = new DefaultBeanDefinition(id, clazz);
            BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, id);

            BeanDefinitionUtils.registerBeanDefinition(holder, register);
            count++;
        }
        return count;
    }
}
