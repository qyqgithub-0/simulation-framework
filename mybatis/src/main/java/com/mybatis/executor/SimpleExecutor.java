package com.mybatis.executor;

import com.mybatis.config.Configuration;
import com.mybatis.config.MappedStatement;
import com.mybatis.parse.GenericTokenParser;
import com.mybatis.pool.PooledDataSource;
import com.mybatis.reflect.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:12
 */
public class SimpleExecutor implements Executor {

    private Configuration configuration;
    private PooledDataSource pooledDataSource;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
        //初始化数据源
        pooledDataSource = new PooledDataSource(configuration.getEnvironment());
    }

    @Override
    public Object query(MappedStatement mappedStatement, Object parameter) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //从连接池中获取连接
            connection = pooledDataSource.getConnection();
            String sql = mappedStatement.getSql();

            //GenericTokenParser.parse(sql)：将#{}替换为?
            preparedStatement = connection.prepareStatement(GenericTokenParser.parse(sql));
            //设置参数，上层是由selectOne调用，因此只有一个参数
            if (parameter instanceof Integer) {
                preparedStatement.setInt(1, (Integer) parameter);
            } else if (parameter instanceof String) {
                preparedStatement.setString(1, (String) parameter);
            }
            resultSet = preparedStatement.executeQuery();

            //处理结果集，将ResultSet映射到Java对象
            return mappingResultSet(resultSet, mappedStatement.getResultType());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 把resultSet结果集映射到对象中
     * @param resultSet 结果集
     * @param resultType 被映射到的对象的路径
     * @return 处理后的对象
     */
    public Object mappingResultSet(ResultSet resultSet, String resultType) {
        try {
            Class<?> clazz = Class.forName(resultType);
            if (resultSet.next()) {
                Object entity = clazz.getDeclaredConstructor().newInstance();
                Reflection.setPropertiesToBeanFromResultSet(entity, resultSet);

                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
