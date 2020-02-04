package com.yefeng.mapper;

import com.yefeng.pojo.Bgm;

import java.util.List;

public interface BgmMapper {
    int deleteByPrimaryKey(String id);

    int insert(Bgm record);

    int insertSelective(Bgm record);

    Bgm selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Bgm record);

    int updateByPrimaryKey(Bgm record);

    List<Bgm> queryBgmList();
}
