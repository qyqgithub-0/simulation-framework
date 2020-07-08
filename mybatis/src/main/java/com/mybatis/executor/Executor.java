package com.mybatis.executor;

import com.mybatis.config.MappedStatement;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 13:11
 */
public interface Executor {

    Object query(MappedStatement mappedStatement, Object parameter);
}
