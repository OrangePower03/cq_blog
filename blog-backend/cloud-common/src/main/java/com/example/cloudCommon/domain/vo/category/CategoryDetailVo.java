package com.example.cloudCommon.domain.vo.category;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDetailVo implements BaseVo {
    private Long id;
    private String name;
    private String description;
    private String status;
}
