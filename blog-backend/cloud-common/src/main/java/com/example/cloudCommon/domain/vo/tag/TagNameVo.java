package com.example.cloudCommon.domain.vo.tag;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagNameVo implements BaseVo {
    Long id;
    String name;
}
