package com.yefeng.mapper;

import com.yefeng.pojo.UserFan;

public interface UserFanMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFan record);

    int insertSelective(UserFan record);

    UserFan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserFan record);

    int updateByPrimaryKey(UserFan record);
}