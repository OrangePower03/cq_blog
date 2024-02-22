package com.example.cloudCommon.domain.vo.category;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

@Data
public class ExportCategoryVo implements BaseVo {
    @ExcelProperty("分类名")
    private String name;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("状态0:正常,1禁用")
    private String status;
}
