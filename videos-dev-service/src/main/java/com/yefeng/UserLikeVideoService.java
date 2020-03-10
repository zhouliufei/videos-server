package com.yefeng;

import com.yefeng.dto.PageResult;
import com.yefeng.dto.ShowLikeVideoInputDTO;
import com.yefeng.util.JsonResult;

public interface UserLikeVideoService {

    /**
     * 用户点赞视频
     * @return
     */
    void userLikeVideos(String userId,String videoId,String videoCreaterId);
    /**
     * 用户取消点赞视频
     * @return
     */
    void userUnLikeVideos(String userId,String videoId,String videoCreaterId);

    /**
     * 查询用户是否喜欢当前视频
     * @return
     */
    JsonResult queryUserLikeStatus(String loginUserId, String videoId, String publishId);

    /**
     * 查询用户收藏视频的接口
     */
    PageResult queryLikeVideo(ShowLikeVideoInputDTO showLikeVideoDTO);

    /**
     * 展示用户关注者发布的视频
     */
    PageResult queryFollowVideo(ShowLikeVideoInputDTO showLikeVideoDTO);
}
