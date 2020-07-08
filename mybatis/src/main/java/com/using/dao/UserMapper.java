package com.using.dao;

import com.using.entity.User;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 8:26
 */
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
