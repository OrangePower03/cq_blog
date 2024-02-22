package com.example.cloudAdminUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudCommon.domain.eitity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-11-20 20:58:31
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    int addRoleWithMenus(@Param("roleId") Long RoleId,
                         @Param("menuIds")List<Long> menuIds);

    List<Long> getAllMenuIdByRoleId(@Param("roleId") Long roleId);

    int deleteSomeMenuId(@Param("roleId") Long RoleId,
                         @Param("menuIds")List<Long> menuIds);
}
