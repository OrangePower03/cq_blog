package com.example.cloudCommon.domain.dto.article;

import com.example.cloudCommon.domain.dto.BaseDto;
import lombok.Data;

@Data
@Deprecated
public class ArticleListDto implements BaseDto {

    int pageNum;

    int pageSize;

    String title;

    String summary;
}
