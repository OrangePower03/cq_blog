package com.example.cloudAdminUser.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudAdminUser.mapper.MenuMapper;
import com.example.cloudAdminUser.mapper.RoleMapper;
import com.example.cloudAdminUser.mapper.UserMapper;
import com.example.cloudCommon.domain.dto.user.LoginDto;
import com.example.cloudCommon.domain.dto.user.RegistryDto;
import com.example.cloudCommon.domain.dto.user.UserDto;
import com.example.cloudCommon.domain.dto.user.UserInfoDto;
import com.example.cloudCommon.domain.eitity.*;
import com.example.cloudCommon.domain.vo.common.PageVo;
import com.example.cloudCommon.domain.vo.menu.MenuInfoVo;
import com.example.cloudCommon.domain.vo.menu.MenuListVo;
import com.example.cloudCommon.domain.vo.menu.MenuVo;
import com.example.cloudCommon.domain.vo.role.RoleUserVo;
import com.example.cloudCommon.domain.vo.user.BlogUserLoginVo;
import com.example.cloudCommon.domain.vo.user.UpdateUserVo;
import com.example.cloudCommon.domain.vo.user.UserInfoVo;
import com.example.cloudCommon.domain.vo.user.UserVo;
import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.exception.SystemException;
import com.example.cloudCommon.service.admin.ArticleService;
import com.example.cloudCommon.service.admin.UserService;
import com.example.cloudCommon.utils.*;
import com.example.cloudCommon.utils.Redis.StringRedisUtil;
import io.jsonwebtoken.lang.Strings;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-09-12 19:22:10
 */
@Service("userService")
@DubboService
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Resource
    private AuthenticationManager manager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private StringRedisUtil redis;

    @Autowired
    private UserMapper userMapper;

    @DubboReference(check=false)
    private ArticleService articleService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authentication=new
                UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticateResult = manager.authenticate(authentication);
        if(Objects.isNull(authenticateResult)){
            throw new SystemException(AppHttpCodeEnum.AUTHENTICATE_ERROR);
        }

        LoginUser loginUser= (LoginUser) authenticateResult.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        //获取令牌
        String token = JwtUtil.createJWT(id);
        //存入redis
        redis.set(SystemConstants.USER_LOGIN+id,loginUser);
        //封装vo响应
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        return ResponseResult.okResult(new BlogUserLoginVo(token,userInfoVo));
    }

    @Override
    public ResponseResult logout() {
        String id= SecurityUtils.getUserId().toString();
        redis.delete(SystemConstants.USER_LOGIN + id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserInfo() {
        User user = SecurityUtils.getLoginUser().getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult register(RegistryDto registryDto) {
        addUserVerify(registryDto);
        User user=BeanCopyUtils.copyBean(registryDto,User.class);
        user.setPassword(passwordEncoder.encode(registryDto.getPassword()));

        int status = userMapper.insert(user);
        if(status > 0) {
            return ResponseResult.okResult();
        }
        throw new SystemException(AppHttpCodeEnum.REGISTER_FAIL);
    }

    @Override
    public ResponseResult adminLogin(LoginDto user) {
        UsernamePasswordAuthenticationToken authentication=new
                UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticateResult = manager.authenticate(authentication);
        if(Objects.isNull(authenticateResult)){
            throw new SystemException(AppHttpCodeEnum.AUTHENTICATE_ERROR);
        }

        LoginUser loginUser= (LoginUser) authenticateResult.getPrincipal();
        String type = loginUser.getUser().getType();
        if(type.equals("1")) {

        }
        String id = loginUser.getUser().getId().toString();
        //获取令牌
        String token = JwtUtil.createJWT(id);
        //存入redis
        redis.set(SystemConstants.ADMIN_LOGIN+id,loginUser);
        return ResponseResult.okResult(Map.of("token",token));
    }

    @Override
    public ResponseResult getInfo() {
        String role = SecurityUtils.getLoginUser().getRole();
        List<String> menus;
        if(role.equals(SystemConstants.ADMIN)) {
            menus = menuMapper.findAllMenu();
        }
        else {
            menus = menuMapper.findAllMenuByUserId(SecurityUtils.getUserId());
        }
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(SecurityUtils.getLoginUser().getUser(), UserInfoVo.class);
        MenuInfoVo menuInfoVo = new MenuInfoVo(menus, List.of(role), userInfoVo);
        return ResponseResult.okResult(menuInfoVo);
    }

    @Override
    public ResponseResult updateInfo(UserInfoDto userInfoDto) {
        if(!SecurityUtils.getUserId().equals(userInfoDto.getId())) {
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        DtoVerifyUtils.verifyNonNull(userInfoDto);
        User user = BeanCopyUtils.copyBean(userInfoDto, User.class);
        userMapper.updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminLogout() {
        String id= SecurityUtils.getUserId().toString();
        redis.delete(SystemConstants.ADMIN_LOGIN + id);
        return ResponseResult.okResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseResult getRouters() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, 0L).orderByDesc(Menu::getOrderNum);
        List<Menu> menus = menuMapper.selectList(wrapper);
        List<MenuVo> menuVoList=new ArrayList<>();
        for (Menu menu : menus) {
            MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
            if(!menu.getMenuType().equals(SystemConstants.MENU_BUTTON)) {
                menuVoList.add(menuVo);
            }
            getRecursiveRouters(menuVo);
        }
        MenuListVo menuListVo=new MenuListVo(menuVoList);
        return ResponseResult.okResult(menuListVo);
    }

    @SuppressWarnings("unchecked")
    private void getRecursiveRouters(MenuVo menuVo) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,menuVo.getId())
                   .in(Menu::getMenuType,SystemConstants.MENU_DIR,SystemConstants.MENU_MENU)
                   .orderByDesc(Menu::getOrderNum);
        List<Menu> childrenMenu = menuMapper.selectList(wrapper);
        if(childrenMenu.size() > 0) {
            List<MenuVo> menuVoList = BeanCopyUtils.copyBeans(childrenMenu, MenuVo.class);
            menuVo.setChildren(menuVoList);
            for (MenuVo childMenu : menuVoList) {
                getRecursiveRouters(childMenu);
            }
        }
    }

    @Override
    public ResponseResult upload(MultipartFile file) {
        String url = OssUtils.pushImage(file);
        return ResponseResult.okResult(url);
    }

    @Override
    public ResponseResult getAll(int pageNum, int pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(Strings.hasText(userName),User::getUserName,userName)
                .eq(Strings.hasText(phonenumber),User::getPhonenumber,phonenumber)
                .eq(Strings.hasText(status),User::getStatus,status);
        Page<User> page=new Page<>(pageNum,pageSize);
        IPage<User> userIPage = userMapper.selectPage(page,wrapper);
        List<User> userList = page.getRecords();
        List<UserVo> userVoList = BeanCopyUtils.copyBeans(userList, UserVo.class);
        return ResponseResult.okResult(new PageVo(userVoList,userIPage.getTotal()));
    }

    @Override
    @Transactional
//    @GlobalTransactional
    public ResponseResult addUser(UserDto userDto) {
        DtoVerifyUtils.verifyNonNull(userDto);
        addUserVerify(userDto);
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getPhonenumber, userDto.getPhonenumber());
        if(userMapper.selectCount(wrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
        }
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        List<Long> roleIds = userDto.getRoleIds();
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userMapper.insert(user);
        Long userId = user.getId();
        if(roleIds.size() > 0) {
            userMapper.addUserWithRole(userId,roleIds);
        }
        return ResponseResult.okResult();
    }

    public void addUserVerify(RegistryDto registryDto) {
        if(!registryDto.getEmail().contains("@") || !Strings.hasText(registryDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_ERROR);
        }
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getEmail,registryDto.getEmail());
        if(userMapper.selectCount(wrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        wrapper.clear();
        wrapper.eq(User::getUserName,registryDto.getUserName());
        if(!Strings.hasText(registryDto.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if(userMapper.selectCount(wrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
    }

    @Override
    @GlobalTransactional
    public ResponseResult deleteUser(List<Long> ids) {
        List<Article> articles = articleService.getArticleByUserIds(ids);
        List<Long> articleIds = articles.stream().map(Article::getId).collect(Collectors.toList());
        articleService.deleteArticle(articleIds);
        userMapper.deleteBatchIds(ids);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUser(Long userId) {
        List<Long> roleIds = userMapper.findRoleIdByUserId(userId);
        LambdaQueryWrapper<Role> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus,SystemConstants.ROLE_STATUS_ENABLE);
        List<Role> roleList = roleMapper.selectList(wrapper);
        List<RoleUserVo> roleUserVos = BeanCopyUtils.copyBeans(roleList, RoleUserVo.class);
        User user = userMapper.selectById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(new UpdateUserVo(roleIds,roleUserVos,userInfoVo));
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UserDto userDto) {
        DtoVerifyUtils.verifyNonNull(userDto);
        addUserVerify(userDto);
        Long userId = userDto.getId();
        List<Long> newRoleIds = userDto.getRoleIds();
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        List<Long> oldRoleIds = userMapper.findRoleIdByUserId(userId);
        List<Long> addRoleIds = newRoleIds.stream()
                .filter(roleId -> !oldRoleIds.contains(roleId))
                .collect(Collectors.toList());
        if(addRoleIds.size() > 0) {
            userMapper.addUserWithRole(userId,addRoleIds);
        }
        List<Long> deleteRoleIds = oldRoleIds.stream()
                .filter(roleId -> !newRoleIds.contains(roleId))
                .collect(Collectors.toList());
        if(deleteRoleIds.size() > 0) {
            userMapper.deleteUserWithRole(userId,deleteRoleIds);
        }
        userMapper.updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(Long userId, String status) {
        userMapper.updateStatus(userId,status);
        return ResponseResult.okResult();
    }
}
