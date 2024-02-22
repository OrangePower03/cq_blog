package com.example.cloudCommon.domain.vo.article;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo implements BaseVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
