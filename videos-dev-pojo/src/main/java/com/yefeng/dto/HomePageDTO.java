package com.yefeng.dto;

import com.yefeng.pojo.Video;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HomePageDTO {

    private String videoCoverUrl;

    private String userFaceUrl;

    private String nickName;

    private String userId;

    private String id;

    private String videoPath;

    private Integer videoWidth;

    private Integer videoHeight;

    private Long likeCounts;
}
