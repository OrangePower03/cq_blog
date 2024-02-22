package com.example.cloudAdminUser.controller;

import com.example.cloudCommon.domain.dto.role.AddRoleDto;
import com.example.cloudCommon.domain.dto.role.UpdateRoleDto;
import com.example.cloudCommon.service.admin.RoleService;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") String roleId) {
        return roleService.get(roleId);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/list")
    public ResponseResult getAll(int  pageNum, int pageSize, String roleName, String status) {
        return roleService.getAll(pageNum, pageSize, roleName, status);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody Map<String,String> requestParams) {
        return roleService.changeStatus(Long.valueOf(requestParams.get("roleId")), requestParams.get("status"));
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PutMapping
    public ResponseResult updateRole(@RequestBody UpdateRoleDto role) {
        return roleService.updateRole(role);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PostMapping
    public ResponseResult add(@RequestBody AddRoleDto role) {
        return roleService.add(role);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") String roleId) {
        List<String> roleIdList = Arrays.stream(roleId.split(","))
                .collect(Collectors.toList());
        return roleService.deleteRole(roleIdList);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole() {
        return roleService.listAllRole();
    }
}
