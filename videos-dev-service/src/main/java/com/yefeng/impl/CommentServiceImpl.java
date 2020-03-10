package com.yefeng.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yefeng.CommentService;
import com.yefeng.dto.CommentPageInputDTO;
import com.yefeng.dto.CommentVO;
import com.yefeng.dto.PageResult;
import com.yefeng.mapper.CommentMapper;
import com.yefeng.pojo.Comment;
import com.yefeng.util.TimeAgoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void userComment(Comment comment) {
        String Id = UUID.randomUUID().toString();
        comment.setId(Id);
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
    }

    @Override
    public PageResult queryCommentList(CommentPageInputDTO commentPageInputDTO) {
        PageHelper.startPage(commentPageInputDTO.getPage(),commentPageInputDTO.getPageSize());
        List<CommentVO> commentList = commentMapper.queryCommentList(commentPageInputDTO.getVideoId());
        for(CommentVO c:commentList) {
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgo(timeAgo);
        }
        PageInfo<CommentVO> pageList = new PageInfo<>(commentList);
        PageResult result = new PageResult();
        result.setTotal(pageList.getPages());
        result.setRows(commentList);
        result.setPage(commentPageInputDTO.getPage());
        result.setRecords(pageList.getTotal());
        return result;
    }
}
