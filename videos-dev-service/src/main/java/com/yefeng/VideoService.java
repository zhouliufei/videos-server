package com.yefeng;

import com.yefeng.pojo.Bgm;

import java.util.List;

public interface VideoService {

    boolean addLikeCount(String videoId);

    boolean reduceLikeCount(String videoId);

}
