package com.example.cloudCommon.domain.vo.user;

import com.example.cloudCommon.domain.vo.BaseVo;
import com.example.cloudCommon.domain.vo.role.RoleUserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserVo implements BaseVo {
    List<Long> roleIds;
    List<RoleUserVo> roles;
    UserInfoVo user;
}
