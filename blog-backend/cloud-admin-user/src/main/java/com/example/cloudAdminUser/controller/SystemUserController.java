package com.example.cloudAdminUser.controller;

import com.example.cloudCommon.domain.dto.user.UserDto;
import com.example.cloudCommon.service.admin.UserService;
import com.example.cloudCommon.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/user")
public class SystemUserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/list")
    public ResponseResult getAll(int pageNum, int pageSize, String userName, String phonenumber, String status) {
        return userService.getAll(pageNum, pageSize, userName, phonenumber, status);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @GetMapping("/{id}")
    public ResponseResult getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody Map<String,String> map) {
        return  userService.changeStatus(Long.valueOf(map.get("userId")),map.get("status"));
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PutMapping
    public ResponseResult updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @PostMapping
    public ResponseResult addUser(@RequestBody UserDto addUserDto) {
        return userService.addUser(addUserDto);
    }

    @PreAuthorize("@ppp.hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        return userService.deleteUser(idList);
    }
}
