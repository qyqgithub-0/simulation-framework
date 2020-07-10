package com.mybatis.proxy;

import com.mybatis.binding.MapperMethod;
import com.mybatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 15:23
 */
@Slf4j
public class MapperProxy implements InvocationHandler, Serializable {

    private final SqlSession sqlSession;
    private final Class<?> mapperInterface;

    public MapperProxy(SqlSession sqlSession) {
        this(sqlSession, null);
    }

    public MapperProxy(SqlSession sqlSession, Class<?> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info(method.getName() + "'s is " + Arrays.toString(args));
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                log.info("Object.class.equals(method.getDeclaringClass())");
                return method.invoke(this, args);
            } else if (isDefaultMethod(method)) {
                log.info("isDefaultMethod(method)");
                return null;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        log.info("ready to get the MapperMethod");
        final MapperMethod mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
        return mapperMethod.execute(sqlSession, args);
        /*
        log.info(method.getName() + " is called. The return type is " + method.getReturnType());

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
         */
    }

    private boolean isDefaultMethod(Method method) {
        return ((method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC)
                && method.getDeclaringClass().isInterface();
    }
}
