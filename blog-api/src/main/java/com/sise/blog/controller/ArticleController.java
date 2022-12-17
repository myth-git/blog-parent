package com.sise.blog.controller;

import com.sise.blog.common.aop.LogAnnotation;
import com.sise.blog.common.cache.Cache;
import com.sise.blog.service.ArticleService;
import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.ArticleParam;
import com.sise.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//json数据交互
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    //Result是统一结果返回
    @PostMapping
    //加上此注解 代表要对此接口记录日志
    @LogAnnotation(module="文章",operator="获取文章列表")
    @Cache(expire = 5 * 60 * 1000,name = "listArticle")
    public Result articles(@RequestBody PageParams pageParams){
        //int i = 10/0;
        return articleService.listArticlesPage(pageParams);
    }

    /**
     * 首页 最热文章，根据浏览量
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public Result HotArticles(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章，根据时间
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5 * 60 * 1000,name = "new_article")
    public Result NewArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 文章详情
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }
    /**
     * 发布文章
     */
    @PostMapping("publish")
    public Result  publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }



}
