package com.mybatis.pool;

import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:17
 */
@Data
public class ConnectionPool implements InvocationHandler {

    private Connection realConnection;
    private Connection proxyConnection;
    private PooledDataSource dataSource;
    private static final String CLOSE = "close";

    public ConnectionPool(Connection realConnection, PooledDataSource dataSource) {
        this.realConnection = realConnection;
        this.dataSource = dataSource;
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
                new Class<?>[] {Connection.class}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.equals(CLOSE)) {
            dataSource.closeConnection(this);
        }
        return method.invoke(realConnection, args);
    }
}
