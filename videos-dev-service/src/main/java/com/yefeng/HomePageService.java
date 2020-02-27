package com.yefeng;

import com.yefeng.dto.HomePageDTO;
import com.yefeng.dto.PageInputDTO;
import com.yefeng.dto.PageResult;
import com.yefeng.dto.VideoPageInputDTO;
import com.yefeng.pojo.Bgm;

import java.util.List;

public interface HomePageService {
    /**
     * @Description 获取首页视频展示列表
     */
    PageResult queryHomePageList(VideoPageInputDTO pageInput);
    /**
     * @Description 获取热搜词列表
     */
    List<String> queryHot();
}
