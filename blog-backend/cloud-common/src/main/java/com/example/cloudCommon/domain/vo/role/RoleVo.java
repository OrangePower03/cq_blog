package com.example.cloudCommon.domain.vo.role;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

@Data
public class RoleVo implements BaseVo {
    Long id;
    String remark;
    String roleKey;
    String roleName;
    int roleSort;
    String status;
}
