package com.mybatis.binding;

import com.mybatis.config.Configuration;
import com.mybatis.config.MappedStatement;
import com.mybatis.exception.BindingException;
import com.mybatis.mapping.SqlCommandType;
import com.mybatis.parse.GenericTokenParser;
import com.mybatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/8 18:29
 */
@Slf4j
public class MapperMethod {

    private final Configuration configuration;
    private final Class<?> mapperInterface;
    private final String sqlId;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) {
        this.mapperInterface = mapperInterface;
        this.configuration = configuration;
        this.sqlId = method.getDeclaringClass().getName() + "." + method.getName();
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result = null;
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(sqlId);     //得到语句，主要是为了得到什么类型的语句
        //除了select，其余最终都会调用到SimpleExecutor的update方法
        switch (mappedStatement.getSqlCommandType()) {
            case INSERT: {
                log.info("prepare insert statement, parameters is " + Arrays.toString(args));
                result = sqlSession.insert(sqlId, args[0]);
                break;
            }
            case UPDATE: {
                log.info("prepare update statement, parameters is " + Arrays.toString(args));
                result = sqlSession.update(sqlId, args[0]);
                break;
            }
            case DELETE: {
                log.info("prepare delete statement, parameters is " + Arrays.toString(args));
                result = sqlSession.delete(sqlId, args[0]);
                break;
            }
            case SELECT: {
                log.info("prepare select statement");
                result = sqlSession.selectOne(sqlId, args == null ? null : args[0]);
                break;
            }
        }
        return result;
    }
}
