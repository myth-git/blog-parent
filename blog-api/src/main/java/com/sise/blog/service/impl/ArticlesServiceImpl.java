package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.ArticlesDao;
import com.sise.common.pojo.Articles;
import com.sise.blog.service.ArticlesService;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.QueryPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 0:37
 */
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesDao,Articles> implements ArticlesService {

    @Autowired
    private ArticlesDao articlesDao;

    @Override
    public Page<ArticlesVO> findHomePage(QueryPageVO queryPageVO) {
        Page<ArticlesVO> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(queryPageVO.getQueryString() != null, "content", queryPageVO.getQueryString());
        page.setTotal(articlesDao.selectCount(queryWrapper));
        page.setRecords(articlesDao.findHomePage(queryPageVO));
        return page;
    }
}
