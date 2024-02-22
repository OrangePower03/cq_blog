package com.example.cloudCommon.domain.vo.menu;

import com.example.cloudCommon.domain.vo.BaseVo;
import com.example.cloudCommon.domain.vo.user.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuInfoVo implements BaseVo {
    List<String> permissions;
    List<String> roles;
    UserInfoVo user;

}
