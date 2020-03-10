package com.yefeng.dto;

import com.yefeng.pojo.Comment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentVO extends Comment {

    private String faceImage;

    private String nickName;

    private String timeAgo;
}
