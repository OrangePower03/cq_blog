package com.example.cloudblogarticle.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.cloudCommon.domain.eitity.Article;
import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.service.ArticleService;
import com.example.cloudCommon.utils.BeanUtil;
import com.example.cloudCommon.utils.Redis.HashRedisUtil;
import com.example.cloudblogarticle.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RedisJob implements Job {
    @Autowired
    private HashRedisUtil redisCache;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ArticleService articleService = (ArticleService) BeanUtil.getBean(ArticleService.class);
        HashRedisUtil redisCache = (HashRedisUtil) BeanUtil.getBean(HashRedisUtil.class);
        Map<String, Object> articleViewCount = redisCache.getAll(SystemConstants.ARTICLE_VIEW_COUNT);
        if(Objects.isNull(articleViewCount)) {
            addAllViewCount();
            return ;
        }
        List<Article> articleList = articleViewCount.entrySet().stream()
                .map(articleCount -> {
                    Article article = new Article();
                    article.setId(Long.valueOf(articleCount.getKey()));
                    article.setViewCount(Long.valueOf(articleCount.getValue().toString()));
                    return article;
                }).collect(Collectors.toList());
        articleService.updateBatchById(articleList);
        log.info("浏览量数据已成功刷入数据库");
    }

    private void addAllViewCount() {
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        wrapper.select(Article::getId,Article::getViewCount);
        List<Article> articles = articleMapper.selectList(wrapper);
        articles.forEach(article -> redisCache.set(
                             SystemConstants.ARTICLE_VIEW_COUNT,
                             article.getId().toString(),
                             Math.toIntExact(article.getViewCount())));
        log.info("Redis 缓存初始化完成");
    }
}
