package com.example.cloudCommon.domain.vo.comment;

import com.example.cloudCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenCommentVo implements BaseVo {
    private Long id;
    //文章id
    private Long articleId;
    //根评论id，因为评论不会有三级评论，所以必须要有根评论
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private Date createTime;
    //评论谁的用户名
    private String toCommentUserName;
    //发表评论的用户名
    private String username;
}
