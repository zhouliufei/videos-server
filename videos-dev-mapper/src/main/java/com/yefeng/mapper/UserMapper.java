package com.yefeng.mapper;

import com.yefeng.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int queryIsExistByColumn(@Param("tableName") String tableName,
                             @Param("column")String column,
                             @Param("username") String username);

    User getUser(@Param("username") String username,
                 @Param("password") String password);
}