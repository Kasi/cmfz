package com.cmfz.service;

import com.cmfz.entity.User;

import java.util.List;


/**
 * @className: UserService
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-23 9:13
 * @Version 1.0
 */


public interface UserService {


    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectBySelective(User user);
}

