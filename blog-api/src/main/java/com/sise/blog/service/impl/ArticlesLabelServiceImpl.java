package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.ArticlesLabelDao;
import com.sise.blog.service.ArticlesLabelService;
import com.sise.common.pojo.ArticlesLabel;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.QueryPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/13 0:40
 */
@Service
public class ArticlesLabelServiceImpl extends ServiceImpl<ArticlesLabelDao, ArticlesLabel> implements ArticlesLabelService {
    @Autowired
    private ArticlesLabelDao articlesLabelDao;

    @Transactional(rollbackFor = Exception.class)//要么全部执行成功，要么全部执行失败
    @Override
    public boolean addArticlesLabel(Long id, Integer[] value) {
        ArticlesLabel articlesLabel = new ArticlesLabel();
        articlesLabel.setArticlesId(id);
        for (Integer integer : value) {
            articlesLabel.setLabelId(Long.valueOf(integer));
            articlesLabelDao.insert(articlesLabel);
        }
        return true;
    }


    @Override
    public Page<ArticlesVO> getByLabelId(QueryPageVO queryPageVO) {
        Integer currentPage = queryPageVO.getCurrentPage(); //当前页
        Integer pageSize = queryPageVO.getPageSize(); ////每页记录数
        Integer start = (currentPage - 1) * pageSize;
        //分页条件
        Page<ArticlesVO> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        QueryWrapper<ArticlesLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(queryPageVO.getLabelId() != null, "label_id", queryPageVO.getLabelId());
        page.setTotal(articlesLabelDao.selectCount(queryWrapper));
        page.setRecords(articlesLabelDao.getByTagId(start, pageSize, queryPageVO.getLabelId()));
        return page;
    }
}
