package com.example.cloudCommon.domain.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends RegistryDto {
    Long id;
    String phonenumber;
    String sex;
    String status;
    List<Long> roleIds;
}
