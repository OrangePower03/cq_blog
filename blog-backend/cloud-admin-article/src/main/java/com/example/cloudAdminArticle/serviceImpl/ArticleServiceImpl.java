package com.example.cloudAdminArticle.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudAdminArticle.mapper.ArticleMapper;
import com.example.cloudAdminArticle.mapper.CategoryMapper;
import com.example.cloudAdminArticle.mapper.TagMapper;
import com.example.cloudCommon.domain.dto.article.AddArticleDto;
import com.example.cloudCommon.domain.dto.article.UpdateArticleDto;
import com.example.cloudCommon.domain.eitity.Article;
import com.example.cloudCommon.domain.eitity.Category;
import com.example.cloudCommon.domain.eitity.Comment;
import com.example.cloudCommon.domain.vo.article.*;
import com.example.cloudCommon.domain.vo.common.PageVo;
import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.exception.SystemException;
import com.example.cloudCommon.service.CommentService;
import com.example.cloudCommon.service.admin.ArticleService;
import com.example.cloudCommon.utils.BeanCopyUtils;
import com.example.cloudCommon.utils.DtoVerifyUtils;
import com.example.cloudCommon.utils.Redis.HashRedisUtil;
import com.example.cloudCommon.utils.ResponseResult;
import io.jsonwebtoken.lang.Strings;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@DubboService
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryMapper categoryMapper;

//    @Resource
//    private CommentMapper commentMapper;

    @DubboReference(check=false)
    private CommentService commentService;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private HashRedisUtil redisCache;

    /**
     * 获取热门文章，浏览量前十的降序排序，不要草稿
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseResult getHotArticles() {
        //搜索条件，
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //不要草稿，第一个参数是列名，Lombok插件这样可以获取到列名，0表示已发布
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //通过浏览量进行降序排列
        queryWrapper.orderByDesc(Article::getViewCount);
        //分页查询，第一个参数是当前的页数，第二个是分的页数
        Page<Article> articlePage=new Page<>(1,10);
        this.page(articlePage,queryWrapper);
        //最后的数据会封装到 articlePage 对象里
        List<Article> articles = articlePage.getRecords()
                .stream()
                .map(article -> {
                    Integer viewCount = redisCache.get(SystemConstants.ARTICLE_VIEW_COUNT, article.getId().toString());
                    if(Objects.isNull(viewCount)) {
                        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
                        wrapper.select(Article::getViewCount)
                           .eq(Article::getId,article.getId());
                        viewCount= Math.toIntExact(this.getOne(wrapper).getViewCount());
                    }
                    article.setViewCount(Long.valueOf(viewCount));
                    return article;
                })
                .collect(Collectors.toList());
        //但是前端不需要这么多的数据，只需要id，摘要和浏览量就行了
        List<HotArticleVo> hotArticleVoList = BeanCopyUtils.copyBeans(articles, HotArticleVo.class);
        //利用规定好的一个响应类返回
        return ResponseResult.okResult(hotArticleVoList);
    }

    /**
     * 给前端返回文章，采用分页查询，减少一次性的数据传输量
     * 要求1：只传已上传，置顶文章排在前面
     * 要求2：如果最后的参数不为空，就按照分类查询，同样符合要求1
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseResult getArticles(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //如果categoryId不为空，按照id查询
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,
                Article::getCategoryId,categoryId);
        //已发布
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //可以根据置顶的值降序排序，后期还可以更改置顶的数据来排列
        queryWrapper.orderByDesc(Article::getIsTop);

        //分页
        Page<Article> articlePage=new Page<>(pageNum,pageSize);
        this.page(articlePage,queryWrapper);

        //还要返回分类名称
        List<Article> articleList = articlePage.getRecords();
        articleList.forEach(article -> {
            Category category = categoryMapper.selectById(article.getCategoryId());
            if(Objects.isNull(category)) {
                throw new SystemException(AppHttpCodeEnum.CATEGORY_NO_FOUND);
            }
            article.setCategoryName(category.getName());
            Integer viewCount = redisCache.get(SystemConstants.ARTICLE_VIEW_COUNT, article.getId().toString());
            if(Objects.isNull(viewCount)) {
                LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
                wrapper.select(Article::getViewCount)
                   .eq(Article::getId,article.getId());
                viewCount= Math.toIntExact(this.getOne(wrapper).getViewCount());
            }
            article.setViewCount(Long.valueOf(viewCount));
        });

        List<ArticleListVo> articleListVoList = BeanCopyUtils.copyBeans(articleList, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVoList, articlePage.getTotal());
        return ResponseResult.okResult(pageVo);

    }

    /**
     * 通过点击文章获取文章具体内容和分类等数据
     * @param id 文章的id
     * @return 封装了ArticleDetailVo对象的 ResponseResult对象
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //通过id获取文章
        Article article = this.getById(id);
        if(Objects.isNull(article)){
            throw new SystemException(AppHttpCodeEnum.ARTICLE_NO_FOUND);
        }
        Integer viewCount = redisCache.get(SystemConstants.ARTICLE_VIEW_COUNT, article.getId().toString());
        if(Objects.isNull(viewCount)) {
            LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
            wrapper.select(Article::getViewCount)
                   .eq(Article::getId,id);
            viewCount= Math.toIntExact(this.getOne(wrapper).getViewCount());
        }
        article.setViewCount(Long.valueOf(viewCount));
        //封装vo对象
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //通过文章中的分类id获取分类名
        articleDetailVo.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName());
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incr(SystemConstants.ARTICLE_VIEW_COUNT,id.toString());
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto addArticleDto) {
        DtoVerifyUtils.verifyNonNull(addArticleDto);
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        this.save(article);
        Long articleId = article.getId();
        redisCache.set(SystemConstants.ARTICLE_VIEW_COUNT,articleId.toString(),0);

        List<Long> tags = addArticleDto.getTags();
        if(tags.size() > 0) {
            tagMapper.addArticleWithTags(article.getId(),tags);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAll(int pageNum, int pageSize, String title, String summary) {
        Page<Article> page=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Strings.hasText(title), Article::getTitle, title)
               .like(Strings.hasText(summary), Article::getSummary, summary);
        this.page(page,wrapper);
        List<Article> articleList = page.getRecords();
        List<AdminArticleListVo> adminArticleListVoList = BeanCopyUtils.copyBeans(articleList, AdminArticleListVo.class);
        return ResponseResult.okResult(new PageVo(adminArticleListVoList,page.getTotal()));
    }

    @Override
    public ResponseResult getOneArticle(Long id) {
        Article article = this.getById(id);
        List<Long> tags = tagMapper.getTagsByArticleId(id);
        AdminArticleDetailVo adminArticleDetailVo = BeanCopyUtils.copyBean(article, AdminArticleDetailVo.class);
        adminArticleDetailVo.setTags(tags);
        return ResponseResult.okResult(adminArticleDetailVo);
    }

    @Override
    @Transactional
    public ResponseResult updateArticle(UpdateArticleDto updateArticleDto) {
        Long articleId = updateArticleDto.getId();
        Article article=BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        List<Long> oldTags = tagMapper.getTagsByArticleId(articleId);
        List<Long> newTags = updateArticleDto.getTags();

        oldTags.stream()
                .filter(tagId -> !newTags.contains(tagId))
                .forEach(tagId -> tagMapper.deleteByATid(articleId,tagId));
        newTags.stream()
                .filter(tagId -> !oldTags.contains(tagId))
                .forEach(tagId -> tagMapper.insertByATid(articleId,tagId));
        this.updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    @GlobalTransactional
    public ResponseResult deleteArticle(List<Long> ids) {
        if(Objects.isNull(ids) || ids.size() == 0) {
            return ResponseResult.okResult();
        }
//        tagMapper.deleteByAId(ids);

//        commentMapper.delete(wrapper);
//        commentService.remove(wrapper);  // RPC不能传这玩意
        commentService.deleteByArticleId((ArrayList<Long>) ids);
        ids.forEach(id -> redisCache.deleteOne(SystemConstants.ARTICLE_VIEW_COUNT,id.toString()));

        this.removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ArrayList<Article> getArticleByUserIds(List<Long> userIds) {
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        wrapper.select(Article::getId).in(Article::getCreateBy, userIds);
        return (ArrayList<Article>) list(wrapper);
    }
}
