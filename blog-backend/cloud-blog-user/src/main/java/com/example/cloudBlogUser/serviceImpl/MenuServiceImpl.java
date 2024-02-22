package com.example.cloudBlogUser.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudBlogUser.mapper.MenuMapper;
import com.example.cloudCommon.domain.eitity.Menu;
import com.example.cloudCommon.domain.vo.menu.AdminMenuVo;
import com.example.cloudCommon.domain.vo.menu.MenuRoleListVo;
import com.example.cloudCommon.domain.vo.menu.MenuTreeVo;
import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.exception.SystemException;
import com.example.cloudCommon.service.MenuService;
import com.example.cloudCommon.utils.BeanCopyUtils;
import com.example.cloudCommon.utils.ResponseResult;
import io.jsonwebtoken.lang.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-11-20 20:58:34
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public ResponseResult getAll(String status, String menuName) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Strings.hasText(status), Menu::getMenuName, menuName)
               .like(Strings.hasText(menuName), Menu::getMenuName, menuName);
        List<Menu> menuList = this.list(wrapper);
        List<AdminMenuVo> adminMenuVoList = BeanCopyUtils.copyBeans(menuList, AdminMenuVo.class);
        return ResponseResult.okResult(adminMenuVoList);
    }

    @Override
    public ResponseResult add(Menu menu) {
        this.save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult get(Long id) {
        Menu menu = this.getById(id);
        AdminMenuVo adminMenuVo = BeanCopyUtils.copyBean(menu, AdminMenuVo.class);
        return ResponseResult.okResult(adminMenuVo);
    }

    @Override
    public ResponseResult updateOne(Menu menu) {
        if(menu.getParentId().equals(menu.getId())) {
            throw new SystemException(AppHttpCodeEnum.FORM_ERROR);
        }
        this.updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long id) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, id);
        if(this.count(wrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
        this.removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseResult getTree() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, 0L)
               .orderByDesc(Menu::getOrderNum);
        List<Menu> menus = this.list(wrapper);
        List<MenuTreeVo> menuVoList=new ArrayList<>();
        for (Menu menu : menus) {
            MenuTreeVo menuVo = BeanCopyUtils.copyBean(menu, MenuTreeVo.class);
            menuVo.setLabel(menu.getMenuName());
            if(!menu.getMenuType().equals(SystemConstants.MENU_BUTTON)) {
                menuVoList.add(menuVo);
            }
            getRecursiveRouters(menuVo);
        }
        return ResponseResult.okResult(menuVoList);
    }

    @SuppressWarnings("unchecked")
    private void getRecursiveRouters(MenuTreeVo menuVo) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,menuVo.getId())
                   .in(Menu::getMenuType, SystemConstants.MENU_DIR,SystemConstants.MENU_MENU)
                   .orderByDesc(Menu::getOrderNum);
        List<Menu> childrenMenu = this.list(wrapper);
        if(childrenMenu.size() > 0) {
            List<MenuTreeVo> menuTreeVos = BeanCopyUtils.copyBeans(childrenMenu, MenuTreeVo.class);
            for(int i=0; i<menuTreeVos.size(); i++) {
                menuTreeVos.get(i).setLabel(childrenMenu.get(i).getMenuName());
            }
            menuVo.setChildren(menuTreeVos);
            for (MenuTreeVo childMenu : menuTreeVos) {
                getRecursiveRouters(childMenu);
            }
        }
    }

    @Override
    public ResponseResult getRoleTree(Long roleId) {
        List<Long> menuIds = this.baseMapper.findAllMenuByRoleId(roleId);
        if(menuIds.size() == 0) {
            return ResponseResult.okResult(new MenuRoleListVo(new ArrayList<>(),menuIds));
        }
        List<Menu> menus = this.listByIds(menuIds);
        List<MenuTreeVo> menuTreeVos = BeanCopyUtils.copyBeans(menus, MenuTreeVo.class);
        for(int i=0;i<menuTreeVos.size();i++) {
            menuTreeVos.get(i).setLabel(menus.get(i).getMenuName());
        }
        List<MenuTreeVo> parentMenu = menuTreeVos.stream()
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(0L))
                .collect(Collectors.toList());
        parentMenu.forEach(parent -> getRoleTree(parent,menuTreeVos));
        return ResponseResult.okResult(new MenuRoleListVo(parentMenu,menuIds));
    }

    public void getRoleTree(MenuTreeVo parent, List<MenuTreeVo> menuTreeVos) {
        List<MenuTreeVo> children = menuTreeVos.stream()
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(parent.getId()))
                .collect(Collectors.toList());
        parent.setChildren(children);
        children.forEach(child -> getRoleTree(child,menuTreeVos));
    }
}
