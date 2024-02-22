package com.example.cloudCommon.domain.vo.article;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AdminArticleDetailVo implements BaseVo {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private Long categoryId;
    private String thumbnail;
    private String isTop;
    private String status;
    private Long viewCount;
    private String isComment;
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
    private Integer delFlag;
    private List<Long> tags;
}
