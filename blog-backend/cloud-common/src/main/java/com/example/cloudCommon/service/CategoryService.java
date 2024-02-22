package com.example.cloudCommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cloudCommon.domain.dto.category.CategoryDto;
import com.example.cloudCommon.domain.dto.category.UpdateCategoryDto;
import com.example.cloudCommon.domain.eitity.Category;
import com.example.cloudCommon.utils.ResponseResult;

import java.util.List;

/**
 * 分类表(SgCategory)表服务接口
 *
 * @author makejava
 * @since 2023-09-10 12:39:03
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult export();

    ResponseResult listPage(int pageNum, int pageSize, String name, String status);

    ResponseResult add(CategoryDto categoryDto);

    ResponseResult get(Long id);

    ResponseResult updateOne(UpdateCategoryDto categoryDto);

    ResponseResult delete(List<Long> ids);
}

