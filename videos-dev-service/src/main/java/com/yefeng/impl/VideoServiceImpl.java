package com.yefeng.impl;

import com.yefeng.VideoService;
import com.yefeng.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;


    @Override
    public boolean addLikeCount(String videoId) {
        return videoMapper.addLikeCount(videoId) > 0;
    }

    @Override
    public boolean reduceLikeCount(String videoId) {
        return videoMapper.reduceLikeCount(videoId) > 0;
    }
}
