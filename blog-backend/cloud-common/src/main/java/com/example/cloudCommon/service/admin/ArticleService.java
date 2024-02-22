package com.example.cloudCommon.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cloudCommon.domain.dto.article.AddArticleDto;
import com.example.cloudCommon.domain.dto.article.UpdateArticleDto;
import com.example.cloudCommon.domain.eitity.Article;
import com.example.cloudCommon.utils.ResponseResult;

import java.util.ArrayList;
import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult getHotArticles();

    ResponseResult getArticles(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto addArticleDto);

    ResponseResult getAll(int  pageNum, int pageSize, String title, String summary);

    ResponseResult getOneArticle(Long id);

    ResponseResult updateArticle(UpdateArticleDto updateArticleDto);

    ResponseResult deleteArticle(List<Long> id);

    ArrayList<Article> getArticleByUserIds(List<Long> userIds);
}
