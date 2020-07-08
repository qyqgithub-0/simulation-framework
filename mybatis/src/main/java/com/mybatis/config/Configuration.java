package com.mybatis.config;

import lombok.Data;

import java.util.Map;

/**
 * Mybatis的配置文件，对mybatis-config.xml和mapper文件信息的封装
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 8:20
 */
@Data
public class Configuration {

    private Environment environment;        //存放mybatis-config.xml的配置信息
    private Map<String, MappedStatement> mappedStatementMap;    //封装了mapper.xml
}
