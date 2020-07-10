package com.using;


import com.mybatis.io.Resources;
import com.mybatis.parse.GenericTokenParser;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//        User user = userMapper.selectByPrimaryKey(1);
//        System.out.println(user.getId() + " " + user.getNick() + " " + user.getPhone() + " " + user.getEmail());
//        System.out.println(userMapper.getClass());

        User user = new User();
        user.setId(1);
        user.setNick("aaa");
        user.setEmail("gg@qq.com");
        user.setPhone("13987654321");

//        userMapper.insert(user);
//        userMapper.deleteByPrimaryKey(7);
        userMapper.updateByPrimaryKey(user);
    }
}
