package com.example.cloudAdminArticle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudCommon.domain.eitity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(SgCategory)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-10 12:39:03
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
