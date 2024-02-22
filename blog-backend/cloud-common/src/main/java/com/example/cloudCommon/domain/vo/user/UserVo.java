package com.example.cloudCommon.domain.vo.user;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

import java.util.Date;

@Data
public class UserVo implements BaseVo {
    Long id;
    String userName;
    String nickName;
    String status;
    String email;
    String phonenumber;
    String sex;
    String avatar;
    Long updateBy;
    Date updateTime;
}
