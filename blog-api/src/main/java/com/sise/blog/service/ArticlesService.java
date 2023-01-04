package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.pojo.Articles;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.QueryPageVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 0:37
 */
public interface ArticlesService extends IService<Articles> {
    /**
     * 渲染首页的分页数据(按阅读量降序)
     * @param queryPageVO
     * @return Page<BlogVO>
     */
    Page<ArticlesVO> findHomePage(QueryPageVO queryPageVO);
    /**
     * 按照时间降序获取最新推荐的博客列表
     * @return list
     */
    List<Articles> latestList();

    /**
     * 获取个人后台博客分页列表
     * @param queryPageVO
     * @param id
     * @return
     */
    Page<ArticlesVO> findPersonBlog(QueryPageVO queryPageVO, Long id);
}
