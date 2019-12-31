package com.yefeng.mapper;

import com.yefeng.pojo.SearchRecord;

public interface SearchRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(SearchRecord record);

    int insertSelective(SearchRecord record);

    SearchRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SearchRecord record);

    int updateByPrimaryKey(SearchRecord record);
}