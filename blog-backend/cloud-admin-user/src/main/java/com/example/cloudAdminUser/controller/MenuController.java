package com.example.cloudAdminUser.controller;

import com.example.cloudCommon.domain.eitity.Menu;
import com.example.cloudCommon.service.admin.MenuService;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/list")
    public ResponseResult getAll(String status, String menuName) {
        return menuService.getAll(status,menuName);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/treeselect")
    public  ResponseResult getTree() {
        return menuService.getTree();
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/roleMenuTreeselect/{id}")
    public  ResponseResult getRoleTree(@PathVariable("id") Long roleId) {
        return menuService.getRoleTree(roleId);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        return menuService.add(menu);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") Long id) {
        return menuService.get(id);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PutMapping
    public ResponseResult update(@RequestBody Menu menu) {
        return menuService.updateOne(menu);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @DeleteMapping("/{menuId}")
    public ResponseResult delete(@PathVariable("menuId") Long id) {
        return menuService.delete(id);
    }
}
