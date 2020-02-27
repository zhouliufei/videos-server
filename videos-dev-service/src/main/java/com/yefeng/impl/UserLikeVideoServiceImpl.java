package com.yefeng.impl;

import com.yefeng.UserLikeVideoService;
import com.yefeng.UserService;
import com.yefeng.dto.VideoLikeDTO;
import com.yefeng.mapper.UserLikeVideoMapper;
import com.yefeng.mapper.UserMapper;
import com.yefeng.mapper.VideoMapper;
import com.yefeng.pojo.User;
import com.yefeng.pojo.UserLikeVideo;
import com.yefeng.util.JsonResult;
import com.yefeng.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserLikeVideoServiceImpl implements UserLikeVideoService {

    @Autowired
    private UserLikeVideoMapper userLikeVideoMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void userLikeVideos(String userId, String videoId, String videoCreaterId) {
        //1、点赞关系表插入数据
        String id = UUID.randomUUID().toString();
        UserLikeVideo ulv = new UserLikeVideo();
        ulv.setId(id);
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        userLikeVideoMapper.insert(ulv);
        //2、视频被喜欢数量的增加
        videoMapper.addLikeCount(videoId);
        //3、用户粉丝数量的增加
        userMapper.addReceiveLikeCount(videoCreaterId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void userUnLikeVideos(String userId, String videoId, String videoCreaterId) {
        //1、点赞关系表的删除
        if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(videoId)) {
            return;
        }
        userLikeVideoMapper.deleteByUIdVId(userId,videoId);
        //2、视频被喜欢数量的减少
        videoMapper.reduceLikeCount(videoId);
        //3、用户粉丝数量的减少
        userMapper.reduceReceiveLikeCount(videoCreaterId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JsonResult queryUserLikeStatus(String loginUserId, String videoId, String publishId) {
        User publisher = userService.queryUserInfo(publishId);
        if(publisher == null) {
            return JsonResult.errorMessage("视频发布者不存在");
        }
        VideoLikeDTO videoLikeDTO = new VideoLikeDTO();
        videoLikeDTO.setUser(publisher);
        boolean userLikeVideo = false;
        UserLikeVideo ulv = userLikeVideoMapper.queryUserLikeStatus(loginUserId,videoId);
        if(ulv != null) {
            userLikeVideo = true;
        }
        videoLikeDTO.setUserLikeVideo(userLikeVideo);
        return JsonResult.ok(videoLikeDTO);
    }
}
