package com.using;

import com.mybatis.exception.BuilderException;
import com.mybatis.io.Resources;
import com.mybatis.parse.GenericTokenParser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/8 17:04
 */
public class Test {

    public static void main(String[] args) throws Exception {
        usingDom4j();
//        String resource = "com/using/dao/UserMapper.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        System.out.println(inputStream);
    }

    private static void usingDom4j() throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/main/java/com/using/UserMapper.xml"));
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            System.out.println(element.getText());
        }
    }
}
