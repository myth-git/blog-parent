package com.sise.blog.controller;

import com.sise.blog.service.CategoryService;
import com.sise.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categorys(){
        return categoryService.findAll();
    }

    @GetMapping("detail")
    public Result categorysDetail(){
        return categoryService.findAllDetail();
    }
}
