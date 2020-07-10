package com.mybatis.session;

import com.mybatis.config.Configuration;

import java.sql.Connection;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:04
 */
public interface SqlSession {

    <T> T getMapper(Class<T> type);

    Object selectOne(String sqlId, Object parameter);

    <E> List<E> selectList(String sqlId);

    int update(String sqlId);

    int update(String sqlId, Object parameter);

    int insert(String sqlId);

    int insert(String sqlId, Object parameter);

    int delete(String sqlId);

    int delete(String sqlId, Object parameter);

    Configuration getConfiguration();
}
