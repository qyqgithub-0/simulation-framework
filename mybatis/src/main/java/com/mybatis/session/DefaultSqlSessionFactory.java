package com.mybatis.session;

import com.mybatis.config.Configuration;
import com.mybatis.executor.Executor;
import com.mybatis.executor.SimpleExecutor;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:08
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Executor executor = new SimpleExecutor(configuration);
        return new DefaultSqlSession(configuration, executor);
    }
}
