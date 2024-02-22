package com.example.cloudCommon.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cloudCommon.domain.dto.link.LinkDto;
import com.example.cloudCommon.domain.eitity.Link;
import com.example.cloudCommon.utils.ResponseResult;

import java.util.List;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-09-12 16:45:45
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getAll(int pageNum, int pageSize, String name, String status);

    ResponseResult add(LinkDto linkDto);

    ResponseResult get(Long id);

    ResponseResult updateOne(LinkDto linkDto);

    ResponseResult delete(List<Long> id);
}

