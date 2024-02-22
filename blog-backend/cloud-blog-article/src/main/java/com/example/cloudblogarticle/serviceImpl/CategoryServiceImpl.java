package com.example.cloudblogarticle.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudCommon.domain.dto.category.CategoryDto;
import com.example.cloudCommon.domain.dto.category.UpdateCategoryDto;
import com.example.cloudCommon.domain.eitity.Article;
import com.example.cloudCommon.domain.eitity.Category;
import com.example.cloudCommon.domain.vo.category.CategoryDetailVo;
import com.example.cloudCommon.domain.vo.category.CategoryVo;
import com.example.cloudCommon.domain.vo.category.ExportCategoryVo;
import com.example.cloudCommon.domain.vo.common.PageVo;
import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.service.ArticleService;
import com.example.cloudCommon.service.CategoryService;
import com.example.cloudCommon.utils.BeanCopyUtils;
import com.example.cloudCommon.utils.DtoVerifyUtils;
import com.example.cloudCommon.utils.ResponseResult;
import com.example.cloudCommon.utils.WebUtils;
import com.example.cloudblogarticle.mapper.CategoryMapper;
import io.jsonwebtoken.lang.Strings;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-09-10 12:43:06
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    /**
     * 查询未被禁用且有文章对应的分类，点击分类还能跳转到所有不为草稿的文章
     */
    @Override
    public ResponseResult getCategoryList(){
        //先找文章，前提是为已发布的文章
        LambdaQueryWrapper<Article> articleQueryWrapper=new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleQueryWrapper);//查询所有返回list
        //通过文章找分类
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //通过这个分类找到是否为不被禁用的状态，并且获取到分类的名字并返回
        //需要返回的：id和分类名
        List<Category> categorieList = this.listByIds(categoryIds);
        List<CategoryVo> categoryVoList = categorieList.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_ENABLE.equals(category.getStatus()))
                .map(category -> BeanCopyUtils.copyBean(category, CategoryVo.class))
                .collect(Collectors.toList());
        return ResponseResult.okResult(categoryVoList);
    }

    @Override
    public ResponseResult listAllCategory() {
        List<Category> categoryList = this.list();
        List<CategoryDetailVo> categoryDetailVos = BeanCopyUtils.copyBeans(categoryList, CategoryDetailVo.class);
        return ResponseResult.okResult(categoryDetailVos);
    }

    @Override
    public ResponseResult listPage(int pageNum, int pageSize, String name, String status) {
        Page<Category> page=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(Strings.hasText(name),Category::getName,name)
               .eq(Strings.hasText(status),Category::getStatus,status);
        this.page(page,wrapper);
        List<Category> categoryList = page.getRecords();
        List<CategoryDetailVo> categoryDetailVos = BeanCopyUtils.copyBeans(categoryList, CategoryDetailVo.class);
        return ResponseResult.okResult(new PageVo(categoryDetailVos,page.getTotal()));
    }

    @Override
    public ResponseResult export() {
        HttpServletResponse response = WebUtils.getResponse();
        response.setContentType("application/vnd.ms-excel");
        String fileName = "分类表.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName);
        response.setCharacterEncoding("UTF-8");

        try {
            EasyExcel.write(response.getOutputStream(), ExportCategoryVo.class)
                    .sheet("分类表")
                    .doWrite(BeanCopyUtils.copyBeans(this.list(), ExportCategoryVo.class));
        } catch (Exception e) {
            ResponseResult result=ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult add(CategoryDto categoryDto) {
        DtoVerifyUtils.verifyNonNull(categoryDto);
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        this.save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult get(Long id) {
        Category category = this.getById(id);
        CategoryDetailVo categoryDetailVo = BeanCopyUtils.copyBean(category, CategoryDetailVo.class);
        return ResponseResult.okResult(categoryDetailVo);
    }

    @Override
    public ResponseResult updateOne(UpdateCategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        this.updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(List<Long> ids) {
        this.removeByIds(ids);
        return ResponseResult.okResult();
    }
}
