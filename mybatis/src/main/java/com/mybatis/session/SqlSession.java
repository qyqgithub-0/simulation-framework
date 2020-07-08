package com.mybatis.session;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:04
 */
public interface SqlSession {

    <T> T getMapper(Class<T> type);

    Object selectOne(String sqlId, Object parameter);
}
