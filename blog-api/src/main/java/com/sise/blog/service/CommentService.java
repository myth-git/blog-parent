package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.pojo.Comment;
import com.sise.common.vo.CommentVO;
import com.sise.common.vo.QueryPageVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/15 23:10
 */
public interface CommentService extends IService<Comment> {
    /**
     * 根据blogId查找评论
     * @param id
     * @return
     */
    List<CommentVO> getCommentList(Long id);

    /**
     * 回复评论
     * @param comment
     * @param id
     */
    void replyComment(Comment comment, Long id);

    /**
     * 用户删除评论
     * @param blogId
     * @param commentId
     * @param id
     * @return
     */
    boolean deleteComments(Long blogId, Long commentId, Long id);

    /**
     * 获取管理员后台的评论分页数据
     * @param queryPageVO
     * @return
     */
    Page<CommentVO> adminComments(QueryPageVO queryPageVO);
}
