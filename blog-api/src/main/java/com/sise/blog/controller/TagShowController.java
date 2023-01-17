package com.sise.blog.controller;

import com.sise.blog.service.ArticlesService;
import com.sise.blog.service.LabelService;
import com.sise.common.entity.Result;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 标签展示模块
 * @Author: xzw
 * @Date: 2023/1/16 0:27
 */
@Api(value = "标签展示模块", description = "标签展示模块")
@RequestMapping("/tagShow")
@RestController
@CrossOrigin
public class TagShowController {

    @Autowired
    private ArticlesService articlesService;
    @Autowired
    private LabelService labelService;

    @ApiOperation(value = "根据标签类型分页展示", notes = "返回分页数据")
    @PostMapping("/getById")
    public Result getByTagId(@RequestBody QueryPageVO queryPageVO) {
        if (queryPageVO.getLabelId() == null) {
            return Result.ok("获取标签信息成功", articlesService.findHomePage(queryPageVO));
        }else {
            return Result.ok("根据标签id获取标签信息成功", labelService.getByLabelId(queryPageVO));
        }
    }
}
