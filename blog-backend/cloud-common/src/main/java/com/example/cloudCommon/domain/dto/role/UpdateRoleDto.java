package com.example.cloudCommon.domain.dto.role;

import com.example.cloudCommon.annotation.DtoNoRequest;
import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleDto implements BaseDto {
    Long id;
    String remark;
    String roleKey;
    String roleName;
    int roleSort;
    String status;

    @DtoNoRequest
    List<Long> menuIds;
}
