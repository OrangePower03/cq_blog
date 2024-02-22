package com.example.cloudCommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cloudCommon.domain.eitity.Comment;
import com.example.cloudCommon.utils.ResponseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-09-13 14:56:14
 */
public interface CommentService extends IService<Comment> {

    ResponseResult createComment(Comment comment);

    ResponseResult getCommentList(int commentType, Long articleId, Integer pageNum, Integer pageSize);

    boolean deleteByArticleId(ArrayList<Long> articleIds);
}

