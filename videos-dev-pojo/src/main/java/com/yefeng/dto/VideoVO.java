package com.yefeng.dto;

import com.yefeng.pojo.Video;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class VideoVO extends Video {

    private String faceImage;

    private String nickName;

}
