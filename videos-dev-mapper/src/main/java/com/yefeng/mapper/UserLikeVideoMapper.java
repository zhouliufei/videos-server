package com.yefeng.mapper;

import com.yefeng.pojo.UserLikeVideo;

public interface UserLikeVideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserLikeVideo record);

    int insertSelective(UserLikeVideo record);

    UserLikeVideo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserLikeVideo record);

    int updateByPrimaryKey(UserLikeVideo record);
}