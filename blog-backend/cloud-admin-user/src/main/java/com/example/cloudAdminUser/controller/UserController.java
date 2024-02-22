package com.example.cloudAdminUser.controller;

import com.example.cloudCommon.domain.dto.user.LoginDto;
import com.example.cloudCommon.service.admin.UserService;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getInfo")
    public ResponseResult getInfo() {
        return userService.getInfo();
    }

    @GetMapping("/getRouters")
    public ResponseResult getRouters() {
        return userService.getRouters();
    }

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody LoginDto user) {
        return userService.adminLogin(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return userService.adminLogout();
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody MultipartFile img) {
        return userService.upload(img);
    }
}
