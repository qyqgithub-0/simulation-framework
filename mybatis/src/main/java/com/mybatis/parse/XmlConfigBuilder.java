package com.mybatis.parse;

import com.mybatis.config.Configuration;
import com.mybatis.config.Environment;
import com.mybatis.config.MappedStatement;
import com.mybatis.mapping.SqlCommandType;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * xml解析类，主要解析mybatis-config.xml和mapper.xml
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 12:15
 */
@Slf4j
public class XmlConfigBuilder {

    private SAXReader reader = new SAXReader();
    private Document configDoc;

    public XmlConfigBuilder(InputStream inputStream) {
        try {
            configDoc = reader.read(inputStream);
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 解析xml文档，将得到的数据封装到Configuration对象
     * @return 解析完成的一个配置文件对象
     */
    @SuppressWarnings("unchecked")
    public Configuration parse() {
        log.info("start parsing the mybatis-config.xml file");
        org.dom4j.Element dataSource = configDoc.getRootElement().element("environments").element("environment").element("dataSource");

        Environment environment = new Environment();
        List<org.dom4j.Element> properties = dataSource.elements("property");
        for (org.dom4j.Element property : properties) {
            if ("driver".equals(property.attributeValue("name"))) {
                environment.setDriver(property.attributeValue("value"));
            } else if ("url".equals(property.attributeValue("name"))) {
                environment.setUrl(property.attributeValue("value"));
            } else if ("username".equals(property.attributeValue("name"))) {
                environment.setUsername(property.attributeValue("value"));
            } else if ("password".equals(property.attributeValue("name"))) {
                environment.setPassword(property.attributeValue("value"));
            }
        }
        log.info("data source configuration completed, Prepare to parse the mapper.xml file");

        Map<String, MappedStatement> mappedStatementMap = new ConcurrentHashMap<String, MappedStatement>();
        List<org.dom4j.Element> mappers = configDoc.getRootElement().element("mappers").elements("mapper");
        for (org.dom4j.Element mapper : mappers) {
            String resource = mapper.attributeValue("resource");
            InputStream mapperInputStream = null;
            try {
                mapperInputStream = new FileInputStream("src/main/java/" + resource);
            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
            }
            try {
                //解析mapper
                Document mapperDoc = reader.read(mapperInputStream);
                org.dom4j.Element mapperRoot = mapperDoc.getRootElement();
                String namespace = mapperRoot.attributeValue("namespace");
                List<org.dom4j.Element> sqlElements = mapperRoot.elements();
                for (org.dom4j.Element sqlElement : sqlElements) {
                    String id = "";
                    String resultType = "";
                    String parameterType = "";
                    SqlCommandType sqlCommandType = SqlCommandType.UNKNOWN;

                    switch (sqlElement.getName()) {
                        case "select": sqlCommandType = SqlCommandType.SELECT; break;
                        case "delete": sqlCommandType = SqlCommandType.DELETE; break;
                        case "insert": sqlCommandType = SqlCommandType.INSERT; break;
                        case "update": sqlCommandType = SqlCommandType.UPDATE; break;
                    }

                    id = sqlElement.attributeValue("id");
                    if (id == null) {
                        throw new RuntimeException("sql id is null");
                    }
                    resultType = sqlElement.attributeValue("resultType");
                    parameterType = sqlElement.attributeValue("parameterType");
                    String sql = sqlElement.getText();

                    //将信息封装到MappedStatement
                    MappedStatement mappedStatement = new MappedStatement();
                    mappedStatement.setId(id);
                    mappedStatement.setNamespace(namespace);
                    mappedStatement.setParameterType(parameterType);
                    mappedStatement.setResultType(resultType);
                    mappedStatement.setSql(sql);
                    mappedStatement.setSqlCommandType(sqlCommandType);
                    //加入mappedStatementMap
                    mappedStatementMap.put(namespace + "." + id, mappedStatement);

                    log.info("sql id: " + mappedStatement.getId() + "    sql statement: " + mappedStatement.getSql());
                }
            } catch (DocumentException e) {
                log.error(e.getMessage());
            }
        }

        Configuration configuration = new Configuration();
        configuration.setEnvironment(environment);

        //mapper.xml
        configuration.setMappedStatementMap(mappedStatementMap);
        return configuration;
    }
}
