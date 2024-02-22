package com.example.cloudCommon.domain.vo.menu;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

@Data
public class AdminMenuVo implements BaseVo {
    String icon;
    Long id;
    int isFrame;
    String menuName;
    String menuType;
    int orderNum;
    Long parentId;
    String path;
    String perms;
    String remark;
    String status;
    String visible;
}
