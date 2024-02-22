package com.example.cloudblogarticle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudCommon.domain.eitity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Deprecated
    int deleteArticleWithTagByArticleIds(@Param("ids") List<Long> ids);

    int deleteArticleWithCommentByArticleIds(@Param("ids") List<Long> ids);
}