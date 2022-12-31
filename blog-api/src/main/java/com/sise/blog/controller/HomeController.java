package com.sise.blog.controller;

import com.sise.blog.service.ArticlesService;
import com.sise.common.vo.QueryPageVO;
import com.sise.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页展示模块
 */
@Api(tags = "首页展示模块",value = "首页展示模块", description = "首页展示模块的接口信息")
@RequestMapping("/home")
@RestController
public class HomeController {

    @Autowired
    private ArticlesService articlesService;


    @ApiOperation(value = "首页分页查询", notes = "返回分页数据")
    @PostMapping("/findHomePage")
    public Result findHomePage(@RequestBody QueryPageVO queryPageVO) {
        return Result.ok("首页分页查询成功",articlesService.findHomePage(queryPageVO));
    }
}
