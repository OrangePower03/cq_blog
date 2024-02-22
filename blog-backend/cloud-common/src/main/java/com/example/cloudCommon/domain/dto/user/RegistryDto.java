package com.example.cloudCommon.domain.dto.user;

import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

@Data
public class RegistryDto implements BaseDto {
    String email;
    String password;
    String userName;
    String nickName;
}
