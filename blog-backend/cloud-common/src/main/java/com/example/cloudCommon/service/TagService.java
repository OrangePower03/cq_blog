package com.example.cloudCommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cloudCommon.domain.dto.tag.TagDto;
import com.example.cloudCommon.domain.eitity.Tag;
import com.example.cloudCommon.utils.ResponseResult;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-11-21 19:12:37
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, String name, String remark);

    ResponseResult addTag(TagDto tagDto);

    ResponseResult deleteTag(String ids);

    ResponseResult getTag(Integer id);

    ResponseResult updateTag(TagDto tagVo);

    ResponseResult listAllTag();
}

