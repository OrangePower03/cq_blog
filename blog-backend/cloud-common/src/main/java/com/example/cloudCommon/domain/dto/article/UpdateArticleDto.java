package com.example.cloudCommon.domain.dto.article;

import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UpdateArticleDto implements BaseDto {
    String title;
    String thumbnail;
    String isTop;
    String isComment;
    String content;
    List<Long> tags;
    Long categoryId;
    String summary;
    String status;

    Long id;
    Long createBy;
    Date createTime;
    Long updateBy;
    Date updateTime;
    Long viewCount;
}
