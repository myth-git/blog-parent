package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.blog.dao.mapper.ArticleMapper;
import com.sise.blog.dao.pojo.Article;
import com.sise.blog.service.ArticleService;
import com.sise.blog.vo.ArticleVo;
import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result listArticlesPage(PageParams pageParams) {
        /*
         * 分页查询数据
         * */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //置顶排序和倒序
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //能直接返回吗?不能，一般封装为一个VO类
        List<ArticleVo> articleVoList = copyList(records);
        return Result.success(articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records) {
        //多个对象存在list中
        List<ArticleVo> articleVoList = new ArrayList<>();
        //循环将Article转成ArticleVo
        for (Article record : records) {
            articleVoList.add(copy(record));
        }
        return articleVoList;
    }

    /**
     * 将Article复制到ArticleVo中
     * @param article
     * @return
     */
    private ArticleVo copy(Article article){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        //创建时间类型不一样，将Article中的Long类型转成String类型
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }


}
