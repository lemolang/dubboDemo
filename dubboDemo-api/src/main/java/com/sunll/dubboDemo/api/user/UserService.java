package com.sunll.dubboDemo.api.user;

import com.sunll.dubboDemo.entity.user.User;

import java.util.List;

/**
 * Created by Administrator
 * on 2017/9/8
 */
public interface UserService {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll();
}
