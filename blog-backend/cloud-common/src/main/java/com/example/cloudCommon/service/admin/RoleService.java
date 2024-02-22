package com.example.cloudCommon.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cloudCommon.domain.dto.role.AddRoleDto;
import com.example.cloudCommon.domain.dto.role.UpdateRoleDto;
import com.example.cloudCommon.domain.eitity.Role;
import com.example.cloudCommon.utils.ResponseResult;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-11-20 20:58:33
 */
public interface RoleService extends IService<Role> {

    ResponseResult getAll(int pageNum, int pageSize, String roleName, String status);

    ResponseResult changeStatus(Long roleId, String status);

    ResponseResult add(AddRoleDto role);

    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    ResponseResult deleteRole(List<String> roleIds);

    ResponseResult get(String roleId);

    ResponseResult listAllRole();
}

