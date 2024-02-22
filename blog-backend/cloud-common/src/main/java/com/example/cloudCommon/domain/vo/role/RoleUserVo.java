package com.example.cloudCommon.domain.vo.role;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

import java.util.Date;

@Data
public class RoleUserVo implements BaseVo {
    Long createBy;
    Date createTime;
    int delFlag;
    Long id;
    String remark;
    String roleKey;
    String roleName;
    int roleSort;
    String status;
    Long updateBy;
}
