package com.example.cloudBlogComment.controller;

import com.example.cloudCommon.domain.eitity.Comment;
import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.service.CommentService;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.getCommentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult getLinkCommentList(Integer pageNum,Integer pageSize){
        return commentService.getCommentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult comment(@RequestBody Comment comment){
        return commentService.createComment(comment);
    }
}
