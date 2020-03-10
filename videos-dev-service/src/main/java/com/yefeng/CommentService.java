package com.yefeng;

import com.yefeng.dto.CommentPageInputDTO;
import com.yefeng.dto.PageResult;
import com.yefeng.pojo.Comment;

import java.util.List;

public interface CommentService {
    /**
     * 用户评论的接口
     */
    void userComment(Comment comment);

    /**
     * 获取评论列表的接口
     */
    PageResult queryCommentList(CommentPageInputDTO commentPageInputDTO);
}
