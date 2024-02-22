package com.example.cloudBlogComment.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cloudBlogComment.mapper.CommentMapper;
import com.example.cloudCommon.domain.eitity.Comment;
import com.example.cloudCommon.domain.vo.comment.ChildrenCommentVo;
import com.example.cloudCommon.domain.vo.comment.RootCommentVo;
import com.example.cloudCommon.domain.vo.common.PageVo;
import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.enums.SystemConstants;
import com.example.cloudCommon.exception.SystemException;
import com.example.cloudCommon.service.CommentService;
import com.example.cloudCommon.service.UserService;
import com.example.cloudCommon.utils.BeanCopyUtils;
import com.example.cloudCommon.utils.ResponseResult;
import com.example.cloudCommon.utils.SecurityUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-09-13 14:56:14
 */
@Service("commentService")
@DubboService
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
//    @Resource
//    private UserMapper userMapper;
    @DubboReference(check=false)
    private UserService userService;

    @Override
    public ResponseResult createComment(Comment comment) {
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.COMMENT_NOT_NULL);
        }

        Long id= SecurityUtils.getUserId();
        comment.setCreateBy(id).setUpdateBy(id);
        this.save(comment);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCommentList(int commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //友链评论一块用了，如果文章为null就是调用的友链评论
        boolean linkComment = (commentType == SystemConstants.LINK_COMMENT);

        //先找文章的根评论，再通过根评论找到所有的子评论
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(!linkComment,Comment::getArticleId,articleId)
                    .eq(Comment::getType,commentType)
                    .eq(Comment::getRootId, SystemConstants.COMMENT_ROOT);
        //分页查询
        Page<Comment> commentPage=new Page<>(pageNum,pageSize);
        List<Comment> rootCommentList = this.page(commentPage,queryWrapper).getRecords();

        //通过根评论的id找到所有的子评论，一一封装成对象
        List<RootCommentVo> rootCommentVoList = BeanCopyUtils.copyBeans(rootCommentList, RootCommentVo.class);
        for (RootCommentVo comment : rootCommentVoList) {
            //通过根评论的id获取到所有的子评论
            LambdaQueryWrapper<Comment> query=new LambdaQueryWrapper<>();
            query.eq(Comment::getRootId,comment.getId());
            //把所有的子评论都加入到这个根评论里
            List<ChildrenCommentVo> children = BeanCopyUtils.copyBeans(this.list(query), ChildrenCommentVo.class);
            comment.setChildren(children);

            //添加一些原来没有的属性，比如根评论的用户名
//            comment.setUsername(userMapper.selectById(comment.getCreateBy()).getUserName());
            comment.setUsername(userService.getById(comment.getCreateBy()).getUserName());
            //子评论的用户名和评论哪个评论的用户名
            children.forEach(child->{
//                child.setUsername(userMapper.selectById(child.getCreateBy()).getUserName());
                child.setUsername(userService.getById(child.getCreateBy()).getUserName());
//                child.setToCommentUserName(userMapper.selectById(child.getToCommentUserId()).getUserName());
                child.setToCommentUserName(userService.getById(child.getToCommentUserId()).getUserName());
            });
        }

        PageVo commentVo=new PageVo(rootCommentVoList,commentPage.getTotal());
        return ResponseResult.okResult(commentVo);
    }

    @Override
    public boolean deleteByArticleId(ArrayList<Long> articleIds) {
        LambdaQueryWrapper<Comment> wrapper=new LambdaQueryWrapper<>();
        wrapper.in(Comment::getArticleId, articleIds);
        return remove(wrapper);
    }

}
