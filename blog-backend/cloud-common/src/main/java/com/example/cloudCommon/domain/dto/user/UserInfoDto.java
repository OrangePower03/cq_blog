package com.example.cloudCommon.domain.dto.user;

import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

@Data
public class UserInfoDto implements BaseDto {
    String nickName;
    String avatar;
    String sex;
    String email;
    Long id;
}
