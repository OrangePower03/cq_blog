package com.example.cloudblogarticle.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudCommon.domain.dto.tag.TagDto;
import com.example.cloudCommon.domain.eitity.Tag;
import com.example.cloudCommon.domain.vo.common.PageVo;
import com.example.cloudCommon.domain.vo.tag.TagNameVo;
import com.example.cloudCommon.domain.vo.tag.TagVo;
import com.example.cloudCommon.service.TagService;
import com.example.cloudCommon.utils.BeanCopyUtils;
import com.example.cloudCommon.utils.DtoVerifyUtils;
import com.example.cloudCommon.utils.ResponseResult;
import com.example.cloudblogarticle.mapper.TagMapper;
import io.jsonwebtoken.lang.Strings;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-11-21 19:12:37
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, String name, String remark) {
        Page<Tag> page=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Tag> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(Strings.hasText(name),Tag::getName,name).like(Strings.hasText(remark),Tag::getRemark,remark);
        this.page(page,wrapper);
        List<Tag> tagList = page.getRecords();
        List<TagVo> tagVoList = BeanCopyUtils.copyBeans(tagList, TagVo.class);
        return ResponseResult.okResult(new PageVo(tagVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addTag(TagDto tagDto) {
        DtoVerifyUtils.verifyNonNull(tagDto);
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        this.save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        this.removeByIds(idList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Integer id) {
        Tag tag = this.getOne(new LambdaQueryWrapper<Tag>().eq(Tag::getId, id));
        return ResponseResult.okResult(BeanCopyUtils.copyBean(tag, TagVo.class));
    }

    @Override
    public ResponseResult updateTag(TagDto tagDto) {
        DtoVerifyUtils.verifyNonNull(tagDto);
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        this.updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        List<Tag> tagList = this.list();
        List<TagNameVo> tagNameVos = BeanCopyUtils.copyBeans(tagList, TagNameVo.class);
        return ResponseResult.okResult(tagNameVos);
    }
}
