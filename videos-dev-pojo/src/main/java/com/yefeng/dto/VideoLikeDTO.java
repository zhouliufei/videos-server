package com.yefeng.dto;


import com.yefeng.pojo.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VideoLikeDTO  {

   private User user;

   private boolean userLikeVideo;


}
