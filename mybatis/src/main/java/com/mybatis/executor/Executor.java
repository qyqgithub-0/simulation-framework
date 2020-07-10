package com.mybatis.executor;

import com.mybatis.config.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:11
 */
public interface Executor {

    List<Object> query(MappedStatement mappedStatement, Object parameter);

    int update(MappedStatement ms, Object parameter) throws SQLException;
}
