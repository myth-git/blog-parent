package com.sise.blog.controller;

import com.sise.blog.service.TagService;
import com.sise.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
@Api(value = "标签模块", description = "标签模块的接口信息")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    @ApiOperation(value = "最热标签")
    public Result hot(){
        int limit = 3;
        return tagService.hots(limit);
    }

    @GetMapping
    @ApiOperation(value = "写文章--查询所有标签")
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    @ApiOperation(value = "导航--查询所有标签")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    @ApiOperation(value = "导航--根据id查询标签列表")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
