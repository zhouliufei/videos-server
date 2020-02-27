package com.yefeng.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yefeng.HomePageService;
import com.yefeng.dto.HomePageDTO;
import com.yefeng.dto.PageResult;
import com.yefeng.dto.VideoPageInputDTO;
import com.yefeng.mapper.SearchRecordMapper;
import com.yefeng.mapper.VideoMapper;
import com.yefeng.pojo.SearchRecord;
import com.yefeng.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private SearchRecordMapper searchRecordMapper;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PageResult queryHomePageList(VideoPageInputDTO pageInput) {
        String desc = pageInput.getDesc();
        if(pageInput.getIsSaveRecord() != null && pageInput.getIsSaveRecord() == 1) {
            SearchRecord searchRecord = new SearchRecord();
            searchRecord.setId(UUID.randomUUID().toString());
            searchRecord.setContent(desc);
            searchRecordMapper.insert(searchRecord);
        }
        PageHelper.startPage(pageInput.getPage(),pageInput.getPageSize());
        PageResult pageResult = new PageResult();
        List<HomePageDTO> list = videoMapper.queryHomePageList(desc);
        PageInfo<HomePageDTO> pageInfo = new PageInfo<>(list);
        pageResult.setPage(pageInput.getPage());
        pageResult.setTotal(pageInfo.getPages());
        pageResult.setRows(list);
        pageResult.setRecords(pageInfo.getTotal());
        return pageResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> queryHot() {
        return searchRecordMapper.queryHot();
    }
}
