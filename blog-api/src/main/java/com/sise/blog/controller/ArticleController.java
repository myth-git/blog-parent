package com.sise.blog.controller;

import com.sise.blog.service.ArticleService;
import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Result articles(@RequestBody PageParams pageParams){
        //int i = 10/0;
        return articleService.listArticlesPage(pageParams);
    }


}
