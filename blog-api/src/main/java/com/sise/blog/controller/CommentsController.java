package com.sise.blog.controller;

import com.sise.blog.service.CommentsService;
import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.CommentParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
@Api(value = "文章评论模块", description = "文章评论模块的接口信息")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("article/{id}")
    @ApiOperation(value = "根据文章id查询评论列表")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }

    @PostMapping("create/change")
    @ApiOperation(value = "添加评论")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
