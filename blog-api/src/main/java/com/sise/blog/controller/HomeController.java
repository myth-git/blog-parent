package com.sise.blog.controller;

import com.sise.blog.service.ArticlesService;
import com.sise.blog.service.LabelService;
import com.sise.blog.service.TypeService;
import com.sise.common.vo.QueryPageVO;
import com.sise.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 首页展示模块
 */
@Api(tags = "首页展示模块",value = "首页展示模块", description = "首页展示模块的接口信息")
@RequestMapping("/home")
@RestController
@CrossOrigin
public class HomeController {

    @Autowired
    private ArticlesService articlesService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private LabelService labelService;


    @ApiOperation(value = "首页分页查询", notes = "返回分页数据")
    @PostMapping("/findHomePage")
    public Result findHomePage(@RequestBody QueryPageVO queryPageVO) {
        return Result.ok("首页分页查询成功",articlesService.findHomePage(queryPageVO));
    }

    @ApiOperation(value = "首页分类查询", notes = "返回分类数据")
    @GetMapping("/findType")
    public Result findType() {
        return Result.ok("返回分类数据成功", typeService.findType());
    }

    @ApiOperation(value = "首页标签查询", notes = "返回标签数据")
    @GetMapping("/findLabel")
    public Result findLabel() {
        return Result.ok("返回标签数据成功", labelService.findLabel());
    }

    @ApiOperation(value = "最新文章推荐查询", notes = "返回文章数据")
    @GetMapping("/latestList")
    public Result latestList() {
        return Result.ok("查询最新文章推荐成功", articlesService.latestList());
    }
}
