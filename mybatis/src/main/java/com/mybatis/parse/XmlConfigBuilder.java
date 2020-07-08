package com.mybatis.parse;

import com.mybatis.config.Configuration;
import com.mybatis.config.Environment;
import com.mybatis.config.MappedStatement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * xml解析类，主要解析mybatis-config.xml和mapper.xml
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 12:15
 */
public class XmlConfigBuilder {

    private XPathParser xPathParser;            //xpath解析器

    public XmlConfigBuilder(InputStream inputStream) {
        this.xPathParser = new XPathParser(inputStream);
    }

    /**
     * 解析xml文档，将得到的数据封装到Configuration对象
     * @return 解析完成的一个配置文件对象
     */
    public Configuration parse() {
        Node dataSourceNode = xPathParser.xNode("/configuration/environments/environment/dataSource");

        //数据源属性配置信息
        Properties properties = new Properties();
        NodeList propertyNodeList = dataSourceNode.getChildNodes();
        //System.out.println(propertyNodeList.getLength());
        for (int i = 0; i < propertyNodeList.getLength(); i++) {
            Node propertyNode = propertyNodeList.item(i);
            if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                properties.setProperty(propertyNode.getAttributes().getNamedItem("name").getNodeValue(),
                        propertyNode.getAttributes().getNamedItem("value").getNodeValue());
            }
        }

        //mybatis-config.xml
        Environment environment = new Environment();
        environment.setDriver(properties.getProperty("driver"));
        environment.setPassword(properties.getProperty("password"));
        environment.setUrl(properties.getProperty("url"));
        environment.setUsername(properties.getProperty("username"));

        //mapper映射文件信息
        Map<String, MappedStatement> mappedStatementMap = new ConcurrentHashMap<String, MappedStatement>();
        Node mappersNode = xPathParser.xNode("/configuration/mappers");
        NodeList mapperNodeList = mappersNode.getChildNodes();
        for (int i = 0; i < mapperNodeList.getLength(); i++) {
            Node mapperNode = mapperNodeList.item(i);
            if (mapperNode.getNodeType() == Node.ELEMENT_NODE) {
                //mapper.xml文件的位置
                String resource = mapperNode.getAttributes().getNamedItem("resource").getNodeValue();
                //解析这个文件
//                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
                InputStream mapperInputStream = null;
                try {
                    mapperInputStream = new FileInputStream("src/main/java/" + resource);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                //解析mapper
                this.xPathParser = new XPathParser(mapperInputStream);
                Element element = xPathParser.getDocument().getDocumentElement();
                String namespace = element.getAttribute("namespace");

                NodeList sqlNodeList = element.getChildNodes();

                for (int j = 0; j < sqlNodeList.getLength(); j++) {
                    Node sqlNode = sqlNodeList.item(j);
                    if (sqlNode.getNodeType() == Node.ELEMENT_NODE) {
                        String id = "";
                        String resultType = "";
                        String parameterType = "";

                        Node idNode = sqlNode.getAttributes().getNamedItem("id");
                        if (null == idNode) {
                            throw new RuntimeException("sql id is null");
                        } else {
                            id = sqlNode.getAttributes().getNamedItem("id").getNodeValue();
                        }
                        Node resultTypeNode = sqlNode.getAttributes().getNamedItem("resultType");
                        if (null != resultTypeNode) {
                            resultType = sqlNode.getAttributes().getNamedItem("resultType").getNodeValue();
                        }
                        Node parameterTypeNode = sqlNode.getAttributes().getNamedItem("parameterType");
                        if (null != parameterTypeNode) {
                            parameterType = sqlNode.getAttributes().getNamedItem("parameterType").getNodeValue();
                        }

                        //将信息封装到MappedStatement
                        String sql = sqlNode.getTextContent();
                        MappedStatement mappedStatement = new MappedStatement();
                        mappedStatement.setId(id);
                        mappedStatement.setNamespace(namespace);
                        mappedStatement.setParameterType(parameterType);
                        mappedStatement.setResultType(resultType);
                        mappedStatement.setSql(sql);
                        //加入mappedStatementMap
                        mappedStatementMap.put(namespace + "." + id, mappedStatement);
                    }
                }
            }
        }
        //将解析出来的值赋给Configuration
        Configuration configuration = new Configuration();
        configuration.setEnvironment(environment);

        //mapper.xml
        configuration.setMappedStatementMap(mappedStatementMap);
        return configuration;
    }
}
