package com.mybatis.session;

import com.mybatis.config.Configuration;
import com.mybatis.config.MappedStatement;
import com.mybatis.executor.Executor;
import com.mybatis.proxy.MapperProxy;

import java.lang.reflect.Proxy;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:10
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;
    private final Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    /**
     * 动态代理生成代理对象，由MapperProxy代理，实现了InvocationHandler接口
     * @param interfaceType 接口字节码
     * @param <T> JavaBean泛型
     * @return 代理对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> interfaceType) {
        MapperProxy mapperProxy = new MapperProxy(this);
        //返回代理对象
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class<?>[]{interfaceType},
                mapperProxy);
    }

    @Override
    public Object selectOne(String sqlId, Object parameter) {
        //获取到MappedStatement，交给执行器
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(sqlId);
        return executor.query(mappedStatement, parameter);
    }
}
