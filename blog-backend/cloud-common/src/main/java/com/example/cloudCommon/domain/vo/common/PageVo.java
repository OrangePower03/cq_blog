package com.example.cloudCommon.domain.vo.common;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo implements BaseVo {
    private List<? extends BaseVo> rows;

    private Long total;
}
