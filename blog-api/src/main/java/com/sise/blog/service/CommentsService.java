package com.sise.blog.service;

import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id查询评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 添加评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
