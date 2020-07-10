package com.mybatis.session;

import com.mybatis.config.Configuration;
import com.mybatis.config.MappedStatement;
import com.mybatis.executor.Executor;
import com.mybatis.proxy.MapperProxy;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:10
 */
@Slf4j
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
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class<?>[]{ interfaceType },
                mapperProxy);
    }

    @Override
    public Object selectOne(String sqlId, Object parameter) {
        //获取到MappedStatement，交给执行器查询并且返回第一个元素
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(sqlId);
        List<Object> resultList = executor.query(mappedStatement, parameter);
        return resultList.get(0);
    }

    @Override
    public <E> List<E> selectList(String sqlId) {
        return null;
    }

    @Override
    public int update(String sqlId) {
        return update(sqlId, null);
    }

    @Override
    public int update(String sqlId, Object parameter) {
        //得到MappedStatement，给执行器执行
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(sqlId);
//        log.info(sqlId + ": " + mappedStatement.getSql());
//        log.info(configuration.getMappedStatementMap().toString());
        try {
            return executor.update(mappedStatement, parameter);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insert(String sqlId) {
        return insert(sqlId, null);
    }

    @Override
    public int insert(String sqlId, Object parameter) {
        return update(sqlId, parameter);
    }

    @Override
    public int delete(String sqlId) {
        return update(sqlId, null);
    }

    @Override
    public int delete(String sqlId, Object parameter) {
        return update(sqlId, parameter);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }
}
