package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.pojo.ArticlesLabel;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.QueryPageVO;

/**
 * @Description: 博客标签关联服务层
 * @Author: xzw
 * @Date: 2023/1/13 0:39
 */
public interface ArticlesLabelService extends IService<ArticlesLabel> {
    boolean addArticlesLabel(Long id, Integer[] value);
    /**
     * 根据标签id获取博客分页数据
     * @param queryPageVO
     * @return page
     */
    Page<ArticlesVO> getByLabelId(QueryPageVO queryPageVO);
}
