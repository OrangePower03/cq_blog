package com.example.cloudCommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cloudCommon.utils.ResponseResult;
import com.example.cloudCommon.domain.eitity.Menu;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-11-20 20:58:34
 */
public interface MenuService extends IService<Menu> {

    ResponseResult getAll(String status, String menuName);

    ResponseResult add(Menu menu);

    ResponseResult get(Long id);

    ResponseResult updateOne(Menu menu);

    ResponseResult delete(Long id);

    ResponseResult getTree();

    ResponseResult getRoleTree(Long roleId);
}

