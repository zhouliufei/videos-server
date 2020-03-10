package com.yefeng.controller;

import com.yefeng.CommentService;
import com.yefeng.dto.CommentPageInputDTO;
import com.yefeng.dto.UserLikeInputDTO;
import com.yefeng.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "评论相关业务的接口",tags = "评论相关业务的controller")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "获取评论列表",notes = "获取视频评论列表的接口")
    @PostMapping("/queryCommentList")
    public JsonResult queryCommentList(@RequestBody CommentPageInputDTO commentPageInputDTO) {
        try {
            return JsonResult.ok(commentService.queryCommentList(commentPageInputDTO));
        }catch (Exception e) {
            e.printStackTrace();
            return JsonResult.errorMessage("获取评论失败");
        }
    }

}
