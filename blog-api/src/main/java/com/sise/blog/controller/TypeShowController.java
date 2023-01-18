package com.sise.blog.controller;

import com.sise.blog.service.ArticlesService;
import com.sise.common.entity.Result;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/17 21:36
 */
@Api(value = "分类展示模块", description = "分类展示模块")
@RequestMapping("/typeShow")
@RestController
@CrossOrigin
public class TypeShowController {

    @Autowired
    private ArticlesService articlesService;

    @ApiOperation(value = "根据分类分页展示", notes = "返回分页数据")
    @PostMapping("/getById")
    public Result getByTypeId(@RequestBody QueryPageVO queryPageVO) {
        if (queryPageVO.getTypeId() == null) {
            return Result.ok("获取分类信息成功", articlesService.findHomePage(queryPageVO));
        } else {
            return Result.ok("根据分类id获取信息成功", articlesService.getByTypeId(queryPageVO));
        }
    }

}
