package com.example.cloudBlogUser.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudBlogUser.mapper.RoleMapper;
import com.example.cloudCommon.domain.dto.role.AddRoleDto;
import com.example.cloudCommon.domain.dto.role.UpdateRoleDto;
import com.example.cloudCommon.domain.eitity.Role;
import com.example.cloudCommon.domain.vo.common.PageVo;
import com.example.cloudCommon.domain.vo.role.RoleListVo;
import com.example.cloudCommon.domain.vo.role.RoleUserVo;
import com.example.cloudCommon.domain.vo.role.RoleVo;
import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.service.RoleService;
import com.example.cloudCommon.utils.BeanCopyUtils;
import com.example.cloudCommon.utils.DtoVerifyUtils;
import com.example.cloudCommon.utils.ResponseResult;
import io.jsonwebtoken.lang.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-11-20 20:58:33
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public ResponseResult getAll(int pageNum, int pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Strings.hasText(status), Role::getStatus, status)
               .like(Strings.hasText(roleName), Role::getRoleName, roleName)
               .ne(Role::getRoleKey, SystemConstants.ADMIN);
        Page<Role> page=new Page<>(pageNum,pageSize);
        this.page(page,wrapper);
        List<Role> roleList = page.getRecords();
        List<RoleListVo> roleListVos = BeanCopyUtils.copyBeans(roleList, RoleListVo.class);
        return ResponseResult.okResult(new PageVo(roleListVos,page.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(Long roleId, String status) {
        Role role = new Role();
        role.setId(roleId);
        role.setStatus(status);
        this.updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddRoleDto addRoleDto) {
        DtoVerifyUtils.verifyNonNull(addRoleDto);
        List<Long> menuIds = addRoleDto.getMenuIds();
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        this.save(role);
        if(menuIds.size() > 0) {
            this.baseMapper.addRoleWithMenus(role.getId(),menuIds);
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        DtoVerifyUtils.verifyNonNull(updateRoleDto);
        Long roleId=updateRoleDto.getId();
        List<Long> oldMenuIds = this.baseMapper.getAllMenuIdByRoleId(roleId);
        List<Long> newMenuIds = updateRoleDto.getMenuIds();
        List<Long> deleteMenu = oldMenuIds.stream()
                .filter(menuId -> !newMenuIds.contains(menuId))
                .collect(Collectors.toList());
        this.baseMapper.deleteSomeMenuId(roleId,deleteMenu);
        List<Long> addMenu = newMenuIds.stream()
                .filter(menuId -> !oldMenuIds.contains(menuId))
                .collect(Collectors.toList());
        if(addMenu.size() > 0) {
            this.baseMapper.addRoleWithMenus(roleId,addMenu);
        }
        this.updateById(BeanCopyUtils.copyBean(updateRoleDto,Role.class));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(List<String> roleId) {
        this.removeByIds(roleId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult get(String roleId) {
        Role role = this.getById(roleId);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(role, RoleVo.class));
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus,SystemConstants.ROLE_STATUS_ENABLE);
        List<Role> roleList = this.list(wrapper);
        List<RoleUserVo> roleUserVos = BeanCopyUtils.copyBeans(roleList, RoleUserVo.class);
        return ResponseResult.okResult(roleUserVos);

    }
}
