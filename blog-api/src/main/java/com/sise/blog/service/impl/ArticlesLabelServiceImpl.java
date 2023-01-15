package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.ArticlesLabelDao;
import com.sise.blog.service.ArticlesLabelService;
import com.sise.common.pojo.ArticlesLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/13 0:40
 */
@Service
public class ArticlesLabelServiceImpl extends ServiceImpl<ArticlesLabelDao, ArticlesLabel> implements ArticlesLabelService {
    @Autowired
    private ArticlesLabelDao articlesLabelDao;
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
}
