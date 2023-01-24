package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.blog.dao.mapper.ArticlesDao;
import com.sise.blog.service.ArchivesService;
import com.sise.common.pojo.Articles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/24 22:18
 */
@Service
public class ArchivesServiceImpl implements ArchivesService {

    @Autowired
    private ArticlesDao articlesDao;

    @Override
    public Page<Articles> getArchivesList() {
        Page<Articles> page = new Page<>(1, 50);
        LambdaQueryWrapper<Articles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Articles::getId, Articles::getTitle, Articles::getFirstPicture, Articles::getCreateTime)
                .orderByDesc(Articles::getCreateTime);
        return articlesDao.selectPage(page, queryWrapper);
    }
}
