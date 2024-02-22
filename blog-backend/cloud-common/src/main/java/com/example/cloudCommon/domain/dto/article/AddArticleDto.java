package com.example.cloudCommon.domain.dto.article;

import com.example.cloudCommon.annotation.DtoNoRequest;
import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

import java.util.List;

@Data
public class AddArticleDto implements BaseDto {
    String title;
    String thumbnail;
    String isTop;
    String isComment;
    String content;

    @DtoNoRequest
    List<Long> tags;

    Long categoryId;
    String summary;
    String status;
}
