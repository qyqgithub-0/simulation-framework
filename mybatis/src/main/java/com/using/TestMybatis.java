package com.using;


import com.mybatis.io.Resources;
import com.mybatis.session.SqlSession;
import com.mybatis.session.SqlSessionFactory;
import com.mybatis.session.SqlSessionFactoryBuilder;
import com.using.dao.UserMapper;
import com.using.entity.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/6 16:47
 */
public class TestMybatis {

    public static void main(String[] args) throws Exception {
        //读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //打开SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取mapper接口对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //调用mapper接口方法操作数据库
        User user = userMapper.selectByPrimaryKey(2);
        System.out.println(user);
    }
}
