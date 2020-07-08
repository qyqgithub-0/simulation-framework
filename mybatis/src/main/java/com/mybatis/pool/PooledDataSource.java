package com.mybatis.pool;

import com.mybatis.config.Environment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:18
 */
public class PooledDataSource extends AbstractDataSourcePool {

    private final List<ConnectionPool> idleConnections = new ArrayList<ConnectionPool>();
    private final List<ConnectionPool> activeConnections = new ArrayList<ConnectionPool>();

    private int poolMaxActiveConnections = 10;
    private int poolMaxIdleConnections = 5;
    private int poolTimeToWait = 20000;

    private final Object monitor = new Object();

    public PooledDataSource(Environment environment) {
        super.setUrl(environment.getUrl());
        super.setDriver(environment.getDriver());
        super.setUsername(environment.getUsername());
        super.setPassword(environment.getPassword());
    }

    @Override
    public Connection getConnection() throws SQLException {
        ConnectionPool connectionPool = popConnection(super.getUsername(), super.getPassword());
        return connectionPool.getProxyConnection();
    }

    private ConnectionPool popConnection(String username, String password) throws SQLException {
        boolean countWait = false;
        ConnectionPool connection = null;
        //开始没有连接
        while (connection == null) {
            synchronized (monitor) {
                //有连接
                if (!idleConnections.isEmpty()) {
                    connection = idleConnections.remove(0);
                } else {
                    if (activeConnections.size() < poolMaxActiveConnections) {
                        connection = new ConnectionPool(super.getConnection(), this);
                    }
                }
                if (!countWait) countWait = true;

                try {
                    if (connection == null) {
                        //等待20秒，等待空闲连接，如果线程往连接池放入了一个连接，就唤醒
                        monitor.wait(poolTimeToWait);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        //说明while语句中已经拿到了连接
        if (connection != null) {
            activeConnections.add(connection);
        }
        return connection;
    }

    /**
     * 归还连接
     * @param connection
     */
    public void closeConnection(ConnectionPool connection) {
        synchronized (monitor) {
            if (idleConnections.size() < poolMaxIdleConnections) {
                idleConnections.add(connection);
            }
            //唤醒上面等待获取空闲连接的线程
            monitor.notifyAll();
        }
    }
}
