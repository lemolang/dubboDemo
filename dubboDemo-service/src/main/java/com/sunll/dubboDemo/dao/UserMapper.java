package com.sunll.dubboDemo.dao;

import com.sunll.dubboDemo.entity.user.User;

import java.util.List;

/**
 * Created by Administrator
 * on 2017/9/12
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll();
}
