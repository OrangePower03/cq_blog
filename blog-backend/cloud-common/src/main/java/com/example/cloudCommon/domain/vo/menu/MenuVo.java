package com.example.cloudCommon.domain.vo.menu;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuVo implements BaseVo {
    List<MenuVo> children=new ArrayList<>();
    String component;
    Date createTime;
    String icon;
    Long id;
    String menuName;
    String menuType;
    String orderNum;
    Long parentId;
    String path;
    String perms;
    String status;
    String visible;
}
