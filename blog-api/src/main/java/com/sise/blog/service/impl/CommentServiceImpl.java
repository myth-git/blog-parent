package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.CommentDao;
import com.sise.blog.service.CommentService;
import com.sise.common.pojo.Comment;
import com.sise.common.vo.CommentVO;
import com.sise.common.vo.QueryPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/15 23:11
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<CommentVO> getCommentList(Long id) {
        //获取一级列表
        List<CommentVO> rootList = commentDao.getRootList(id);
        //获取二级列表
        List<CommentVO> childList = commentDao.getChildList(id);
        //父子类列表整合
        return getResultList(rootList, childList);
    }

    @Override
    public void replyComment(Comment comment, Long id) {
        comment.setUid(id);
        if (comment.getParentCommentId() == null) {
            comment.setParentCommentId(-1L);
        }
        commentDao.insert(comment);
    }

    @Override
    public boolean deleteComments(Long blogId, Long commentId, Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getBlogId, blogId)
                    .eq(Comment::getCommentId, commentId)
                    .eq(Comment::getUid, id);
        if (commentDao.selectOne(queryWrapper) == null) {
            //表示该评论不是你评论的
            return false;
        }
        //递归删除子评论
        List<Long> arrayList = new ArrayList<>();
        getDelIds(commentId, arrayList);
        //在删除顶级评论
        arrayList.add(commentId);
        commentDao.deleteBatchIds(arrayList);
        return true;
    }

    @Override
    public Page<CommentVO> adminComments(QueryPageVO queryPageVO) {
        Page<CommentVO> page = new Page<>();
        page.setRecords(commentDao.adminComments(queryPageVO));
        page.setTotal(commentDao.selectCount(null));
        return page;
    }

    private void getDelIds(Long commentId, List<Long> arrayList) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentCommentId, commentId)
                    .select(Comment::getCommentId);
        List<Comment> childrenList = commentDao.selectList(queryWrapper);
        childrenList.stream().forEach(t -> {
            arrayList.add(t.getCommentId());
            //递归添加到list集合中
            this.getDelIds(t.getCommentId(), arrayList);
        });
    }

    private List<CommentVO> getResultList(List<CommentVO> rootList, List<CommentVO> childList) {
        for (CommentVO commentVO : rootList) {
            List<CommentVO> list = new ArrayList<>();
            for (CommentVO vo : childList) {
                if (vo.getParentCommentId().equals(commentVO.getCommentId())){
                    list.add(vo);
                }
            }
            commentVO.setChildren(list);
        }
        return rootList;
    }
}
