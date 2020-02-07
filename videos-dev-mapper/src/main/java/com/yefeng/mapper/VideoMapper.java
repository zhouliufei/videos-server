package com.yefeng.mapper;

import com.yefeng.pojo.Video;
import org.apache.ibatis.annotations.Param;

public interface VideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    String queryCoverPath(String videoId);

    void updateCoverPath(@Param("videoId") String videoId,
                         @Param("coverPath") String uploadDB);
}
