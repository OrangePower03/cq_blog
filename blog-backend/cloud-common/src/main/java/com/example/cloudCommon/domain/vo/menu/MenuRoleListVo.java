package com.example.cloudCommon.domain.vo.menu;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRoleListVo implements BaseVo {
    List<MenuTreeVo> menus;
    List<Long> checkedKeys;
}
