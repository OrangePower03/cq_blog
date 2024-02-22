package com.example.cloudCommon.domain.vo.role;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

@Data
public class RoleListVo implements BaseVo {
    Long id;
    String roleKey;
    String roleName;
    int roleSort;
    String status;
}
