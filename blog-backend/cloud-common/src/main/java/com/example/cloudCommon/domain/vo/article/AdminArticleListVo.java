package com.example.cloudCommon.domain.vo.article;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.Data;

import java.util.Date;

@Data
public class AdminArticleListVo implements BaseVo {
    private Long id;

    //标题
    private String title;

    //文章摘要
    private String summary;

    //所属分类id
    private Long categoryId;

    private int isComment;

    private int isTop;

    private int status;

    //缩略图
    private String thumbnail;

    //访问量
    private Long viewCount;

    private Date createTime;

    //文章内容
    private String content;
}
