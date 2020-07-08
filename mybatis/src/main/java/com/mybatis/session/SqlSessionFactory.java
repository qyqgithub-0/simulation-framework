package com.mybatis.session;

import com.mybatis.config.Configuration;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 8:16
 */
public interface SqlSessionFactory {


    SqlSession openSession();
}
