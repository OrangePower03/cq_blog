package com.example.cloudblogarticle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudCommon.domain.eitity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-11-21 19:12:36
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    List<Long> getTagsByArticleId(@Param("id") Long id);

    int addArticleWithTags(@Param("articleId") Long articleId,
                           @Param("tagIds") List<Long> tagIds);

    int deleteByAId(@Param("articleIds") List<Long> articleIds);

    int deleteByATid(@Param("articleId") Long articleId,
                      @Param("tagId") Long tagId);

    void insertByATid(@Param("articleId") Long articleId,
                      @Param("tagId") Long tagId);

//    void deleteByAId(@Param("articleId") Long articleId);
}
