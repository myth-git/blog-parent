package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.pojo.ArticlesLabel;

/**
 * @Description: 博客标签关联服务层
 * @Author: xzw
 * @Date: 2023/1/13 0:39
 */
public interface ArticlesLabelService extends IService<ArticlesLabel> {
    boolean addArticlesLabel(Long id, Integer[] value);
}
