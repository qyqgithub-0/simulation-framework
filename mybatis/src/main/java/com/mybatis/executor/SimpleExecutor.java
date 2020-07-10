package com.mybatis.executor;

import com.mybatis.config.Configuration;
import com.mybatis.config.MappedStatement;
import com.mybatis.parse.GenericTokenParser;
import com.mybatis.pool.PooledDataSource;
import com.mybatis.reflect.Reflection;
import com.mybatis.util.JdbcUtils;
import com.using.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:12
 */
@Slf4j
public class SimpleExecutor implements Executor {

    private Configuration configuration;
    private PooledDataSource pooledDataSource;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
        //初始化数据源
        pooledDataSource = new PooledDataSource(configuration.getEnvironment());
    }

    @Override
    public List<Object> query(MappedStatement mappedStatement, Object parameter) {
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
            JdbcUtils.close(resultSet, preparedStatement, connection);
        }
        return null;
    }

    /**
     * 真正操作数据库，执行insert、update、delete等方法
     * @param ms 配置文件操作数据库信息的对象
     * @param parameter 参数
     * @return 影响行数
     * @throws SQLException 数据库操作失败
     */
    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pooledDataSource.getConnection();
            String sql = GenericTokenParser.parse(ms.getSql());
            preparedStatement = connection.prepareStatement(sql);
            Class<?> clazz = Class.forName(ms.getParameterType());      //得到参数的字节码

            if (parameter != null) {
                //参数类型一般就是Integer、String和POJO
                if (clazz == Integer.class) {
                    preparedStatement.setInt(1, (Integer) parameter);
                } else if (clazz == String.class) {
                    preparedStatement.setString(1, (String) parameter);
                } else {
                    //类型是对象、需要从里面获取到属性的信息，将信息对应到预编译语句的？之中
                    log.info("sql statement: " + GenericTokenParser.parseSQLStatement(ms.getSql(), parameter));
                    preparedStatement = connection.prepareStatement(GenericTokenParser.parseSQLStatement(ms.getSql(), parameter));
                }
            }
            int row = preparedStatement.executeUpdate();
            log.info("update method succeed to execute, influenced row: " + row);
            return row;

        } catch (Exception e) {
            log.error("update method failed to execute, cause by: " + e.getMessage());
        } finally {
            JdbcUtils.close(null, preparedStatement, connection);
        }
        return 0;
    }

    /**
     * 把resultSet结果集映射到对象中
     * @param resultSet 结果集
     * @param resultType 被映射到的对象的路径
     * @return 处理后的对象集合
     */
    public List<Object> mappingResultSet(ResultSet resultSet, String resultType) {
        try {
            Class<?> clazz = Class.forName(resultType);
            //存放映射对象的集合
            List<Object> resultList = new ArrayList<Object>();
            while (resultSet.next()) {
                Object entity = clazz.getDeclaredConstructor().newInstance();
                Reflection.setPropertiesToBeanFromResultSet(entity, resultSet);
                resultList.add(entity);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
