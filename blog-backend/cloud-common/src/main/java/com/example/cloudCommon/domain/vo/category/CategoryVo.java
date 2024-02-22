package com.example.cloudCommon.domain.vo.category;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo implements BaseVo {
    private Long id;
    //分类名
    private String name;
}
