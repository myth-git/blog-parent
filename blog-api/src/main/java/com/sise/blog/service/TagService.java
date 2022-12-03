package com.sise.blog.service;

import com.sise.blog.vo.Result;
import com.sise.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 写文章--查询所有标签
     * @return
     */
    Result findAll();

    Result findAllDetail();
}
