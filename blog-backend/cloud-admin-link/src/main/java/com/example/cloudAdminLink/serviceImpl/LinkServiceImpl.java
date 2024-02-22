package com.example.cloudAdminLink.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudAdminLink.mapper.LinkMapper;
import com.example.cloudCommon.domain.dto.link.LinkDto;
import com.example.cloudCommon.domain.eitity.Link;
import com.example.cloudCommon.domain.vo.common.PageVo;
import com.example.cloudCommon.domain.vo.link.AdminLinkVo;
import com.example.cloudCommon.domain.vo.link.LinkVo;
import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.exception.SystemException;
import com.example.cloudCommon.service.admin.LinkService;
import com.example.cloudCommon.utils.BeanCopyUtils;
import com.example.cloudCommon.utils.DtoVerifyUtils;
import com.example.cloudCommon.utils.ResponseResult;
import io.jsonwebtoken.lang.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-09-12 16:45:45
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    /**
     * 获取友链，不需要分页，因为友链一般不需要太多，所以不用分页
     * 友链必须要是审核通过的
     */
    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_ACCEPT);
        List<Link> links = this.list(queryWrapper);
        List<LinkVo> linkVoList = BeanCopyUtils.copyBeans(links, LinkVo.class);
        return ResponseResult.okResult(linkVoList);
    }

    @Override
    public ResponseResult getAll(int pageNum, int pageSize, String name, String status) {
        LambdaQueryWrapper<Link> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(Strings.hasText(name),Link::getName, name)
                .eq(Strings.hasText(status),Link::getStatus, status);
        Page<Link> page = new Page<>(pageNum, pageSize);
        this.page(page, wrapper);
        List<Link> links = page.getRecords();
        List<AdminLinkVo> adminLinkVos = BeanCopyUtils.copyBeans(links, AdminLinkVo.class);
        return ResponseResult.okResult(new PageVo(adminLinkVos, page.getTotal()));
    }

    @Override
    public ResponseResult add(LinkDto linkDto) {
        DtoVerifyUtils.verifyNonNull(linkDto);
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        this.save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult get(Long id) {
        assert id != null;
        Link link = this.getById(id);
        assert link != null;
        AdminLinkVo adminLinkVo = BeanCopyUtils.copyBean(link, AdminLinkVo.class);
        return ResponseResult.okResult(adminLinkVo);
    }

    @Override
    public ResponseResult updateOne(LinkDto linkDto) {
        DtoVerifyUtils.verifyNonNull(linkDto);
        assert linkDto.getId() != null;

        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        this.updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(List<Long> ids) {
        if(Objects.nonNull(ids) && ids.size()>0) {
            this.removeByIds(ids);
            return ResponseResult.okResult();
        }
        throw new SystemException(AppHttpCodeEnum.DTO_NULL);
    }
}
