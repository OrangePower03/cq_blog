package com.example.cloudCommon.domain.vo.user;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogUserLoginVo implements BaseVo {
    private String token;

    private UserInfoVo userInfo;
}
