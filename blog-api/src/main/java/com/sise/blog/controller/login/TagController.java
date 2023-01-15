package com.sise.blog.controller.login;

import com.sise.blog.service.LabelService;
import com.sise.common.entity.Result;
import com.sise.common.pojo.Label;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 标签控制层
 * @Author: xzw
 * @Date: 2023/1/12 23:42
 */
@Api(value = "后台标签管理模块", description = "标签管理模块的接口信息")
@RequestMapping("/tag")
@RestController
@CrossOrigin
public class TagController {

    @Autowired
    private LabelService labelService;

    //获取添加博客标签回显
    @ApiOperation(value = "获取标签列表", notes = "返回标签列表数据")
    @GetMapping("/getTagList")
    public Result getTagList() {
        List<Label> labelList = labelService.getTagList();
        return Result.ok("获取标签列表成功", labelList);
    }
}
