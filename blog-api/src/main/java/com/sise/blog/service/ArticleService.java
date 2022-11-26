package com.sise.blog.service;

import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticlesPage(PageParams pageParams);

}
