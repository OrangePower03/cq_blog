package com.example.cloudCommon.domain.vo.link;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

@Data
public class AdminLinkVo implements BaseVo {
    Long id;
    String name;
    String logo;
    String description;
    String address;
    String status;
}
