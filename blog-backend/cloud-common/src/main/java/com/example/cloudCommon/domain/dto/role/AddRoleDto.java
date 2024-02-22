package com.example.cloudCommon.domain.dto.role;

import com.example.cloudCommon.annotation.DtoNoRequest;
import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

import java.util.List;

@Data
public class AddRoleDto implements BaseDto {
    String roleName;
    String roleKey;
    int roleSort;

    @DtoNoRequest
    List<Long> menuIds;
    String remark;
    String status;
}
