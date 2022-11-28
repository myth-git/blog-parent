package com.sise.blog.service;

import com.sise.blog.vo.Result;

public interface CommentsService {
    /**
     * 根据文章id查询评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);
}
