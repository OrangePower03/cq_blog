package com.example.cloudCommon.domain.vo.menu;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuTreeVo implements BaseVo {
    List<MenuTreeVo> children=new ArrayList<>();
    Long id;
    String label;
    Long parentId;
}
