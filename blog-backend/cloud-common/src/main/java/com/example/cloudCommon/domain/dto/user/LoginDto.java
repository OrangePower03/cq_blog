package com.example.cloudCommon.domain.dto.user;

import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

@Data
public class LoginDto implements BaseDto {
    String userName;

    String password;
}
