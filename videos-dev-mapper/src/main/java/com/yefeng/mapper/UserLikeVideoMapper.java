package com.yefeng.mapper;

import com.yefeng.pojo.UserLikeVideo;
import org.apache.ibatis.annotations.Param;

public interface UserLikeVideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserLikeVideo record);

    int insertSelective(UserLikeVideo record);

    UserLikeVideo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserLikeVideo record);

    int updateByPrimaryKey(UserLikeVideo record);

    int deleteByUIdVId(@Param("userId") String userId,
                       @Param("videoId") String videoId);

    UserLikeVideo queryUserLikeStatus(@Param("loginUserId") String loginUserId,
                             @Param("videoId") String videoId);
}
