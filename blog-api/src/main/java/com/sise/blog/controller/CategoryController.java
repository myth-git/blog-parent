package com.sise.blog.controller;

import com.sise.blog.service.CategoryService;
import com.sise.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
@Api(value = "文章分类模块", description = "文章分类模块的接口信息")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @ApiOperation(value = "写文章--展示所有分类")
    public Result categorys(){
        return categoryService.findAll();
    }

    @GetMapping("detail")
    @ApiOperation(value = "导航--文章分类")
    public Result categorysDetail(){
        return categoryService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    @ApiOperation(value = "根据id查询对应文章分类")
    public Result categoryDetailById(@PathVariable("id") Long id){
        return categoryService.categoryDetailById(id);
    }
}
