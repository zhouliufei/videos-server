package com.yefeng;

import com.yefeng.pojo.Bgm;

import java.util.List;

public interface BgmService {
    List<Bgm> queryBgmList();

    Bgm queryBgmById(String audioId);
}
