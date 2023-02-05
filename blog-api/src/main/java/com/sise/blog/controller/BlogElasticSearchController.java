package com.sise.blog.controller;

import com.sise.blog.service.BlogInfoService;
import com.sise.common.entity.Result;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/2/4 20:33
 */
@RestController
@CrossOrigin
@Api(tags = "博客搜索控制类")
@RequestMapping(value = "/search")
public class BlogElasticSearchController {

    @Autowired
    private BlogInfoService blogInfoService;

    @ApiOperation(value = "分页高亮条件查询", notes = "返回分页数据")
    @PostMapping("/searchPage1")
    public Result highLightSearchPage(@RequestBody QueryPageVO queryPageVO) throws IOException {
        return Result.ok("分页高亮条件查询成功", blogInfoService.highLightSearchPage(queryPageVO));
    }
}
