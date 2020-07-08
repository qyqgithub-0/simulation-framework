package com.mybatis.session;

import com.mybatis.config.Configuration;
import com.mybatis.parse.XmlConfigBuilder;

import java.io.InputStream;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 8:16
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) {
        //解析mybatis的xml文件，得到配置对象
        Configuration configuration = new XmlConfigBuilder(inputStream).parse();
        return new DefaultSqlSessionFactory(configuration);
    }
}
