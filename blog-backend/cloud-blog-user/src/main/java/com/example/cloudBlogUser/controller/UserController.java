package com.example.cloudBlogUser.controller;

import com.example.cloudCommon.annotation.Log;
import com.example.cloudCommon.domain.dto.user.RegistryDto;
import com.example.cloudCommon.domain.dto.user.UserInfoDto;
import com.example.cloudCommon.domain.eitity.User;
import com.example.cloudCommon.service.UserService;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/userInfo")
    public ResponseResult getUserInfo(){
        return userService.getUserInfo();
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return userService.login(user);
    }

    @Log(value = "用户登出")
    @PostMapping("/logout")
    public ResponseResult logout(){
        return userService.logout();
    }

    @PostMapping("/user/register")
    public ResponseResult register(@RequestBody RegistryDto registryDto) {
        return userService.register(registryDto);
    }

    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestBody MultipartFile img) {
        return userService.upload(img);
    }

    @PutMapping("/user/userInfo")
    public ResponseResult updateInfo(@RequestBody UserInfoDto userInfoDto) {
        return userService.updateInfo(userInfoDto);
    }
}
