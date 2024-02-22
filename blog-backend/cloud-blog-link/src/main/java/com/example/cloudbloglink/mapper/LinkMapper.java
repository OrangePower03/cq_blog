package com.example.cloudbloglink.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudCommon.domain.eitity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-12 16:45:44
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}
