package com.yefeng;

import com.yefeng.dto.UserLikeInputDTO;
import com.yefeng.pojo.UserLikeVideo;
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
}
