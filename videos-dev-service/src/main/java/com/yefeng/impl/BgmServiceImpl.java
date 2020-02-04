package com.yefeng.impl;

import com.yefeng.BgmService;
import com.yefeng.mapper.BgmMapper;
import com.yefeng.pojo.Bgm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.queryBgmList();
    }

    @Override
    public Bgm queryBgmById(String audioId) {
        return bgmMapper.selectByPrimaryKey(audioId);
    }
}
