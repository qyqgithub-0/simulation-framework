package com.mybatis.config;

import lombok.Data;

/**
 * 封装了mapper.xml
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 12:06
 */
@Data
public class MappedStatement {

    private String namespace;
    private String id;
    private String parameterType;
    private String resultType;
    private String sql;

}
