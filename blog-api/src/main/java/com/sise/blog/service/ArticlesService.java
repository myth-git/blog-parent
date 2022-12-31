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
    Page<ArticlesVO> findHomePage(QueryPageVO queryPageVO);

    List<Articles> latestList();
}
