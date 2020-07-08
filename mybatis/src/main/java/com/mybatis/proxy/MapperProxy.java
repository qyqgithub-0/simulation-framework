package com.mybatis.proxy;

import com.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 15:23
 */
public class MapperProxy implements InvocationHandler {

    private final SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法的返回类型，如调用selectByPrimaryKey则返回一个User
        Class<?> returnType = method.getReturnType();
        if (Collection.class.isAssignableFrom(returnType)) {        //返回类型是否是集合类型的子类
            //表示查询多条数据，返回list
            return null;
        } else if (Map.class.isAssignableFrom(returnType)) {
            //查询多条数据，返回map
            return null;
        } else {
            //返回的是对象，得到sqlId，namespace+方法名
            String sqlId = method.getDeclaringClass().getName() + "." + method.getName();
            return sqlSession.selectOne(sqlId, args == null ? null : args[0]);
        }
    }
}
