package com.example.cloudCommon.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cloudCommon.domain.dto.user.LoginDto;
import com.example.cloudCommon.domain.dto.user.RegistryDto;
import com.example.cloudCommon.domain.dto.user.UserDto;
import com.example.cloudCommon.domain.dto.user.UserInfoDto;
import com.example.cloudCommon.domain.eitity.User;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-09-12 19:22:10
 */
public interface UserService extends IService<User> {

    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult getUserInfo();

    ResponseResult register(RegistryDto registryDto);

    ResponseResult adminLogin(LoginDto user);

    ResponseResult getInfo();

    ResponseResult getRouters();

    ResponseResult adminLogout();

    ResponseResult upload(MultipartFile file);

    ResponseResult getAll(int pageNum, int pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(UserDto userDto);

    ResponseResult deleteUser(List<Long> ids);

    ResponseResult getUser(Long userId);

    ResponseResult updateUser(UserDto userDto);

    ResponseResult changeStatus(Long userId, String status);

    ResponseResult updateInfo(UserInfoDto userInfoDto);
}

