package com.yefeng.mapper;

import com.yefeng.pojo.UserFan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFanMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFan record);

    int insertSelective(UserFan record);

    UserFan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserFan record);

    int updateByPrimaryKey(UserFan record);

    void deleteByUserIdAndAuthorId(@Param("userId") String userId,
                                   @Param("fanId") String authorId);

    List<UserFan> selectByUIdFId(@Param("userId") String userId,
                                 @Param("fanId") String fanId);
}
