package com.example.cloudBlogComment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudCommon.domain.eitity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-13 14:56:14
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    int deleteByArticleId(@Param("articleIds") List<Long> articleIds);
}
