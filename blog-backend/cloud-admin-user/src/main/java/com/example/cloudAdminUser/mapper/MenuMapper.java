package com.example.cloudAdminUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudCommon.domain.eitity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-11-20 20:58:33
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> findAllMenuByUserId(Long userId);

    List<String> findAllMenu();

    List<Long> findAllMenuByRoleId(Long roleId);
}
