package com.test;

import com.bean.xml.XmlBeanDefinitionReader;
import com.context.ApplicationContext;
import com.context.ClassPathXmlApplicationContext;
import com.entity.Student;
import org.dom4j.DocumentException;

import java.io.File;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/30 8:13
 */
public class Main {

    public static void main(String[] args) throws Exception {
//        File file = new File("src/main/resources/applicationContext.xml");
//        System.out.println(file.exists());
//        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader();
//        xmlBeanDefinitionReader.doLoadBeanDefinitions("src/main/resources/applicationContext.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("src/main/resources/applicationContext.xml");
        Student student = (Student) context.getBean("student");
        System.out.println(student);
        System.out.println("run successfully");

    }
}