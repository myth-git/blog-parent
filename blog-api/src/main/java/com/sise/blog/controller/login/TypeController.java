package com.sise.blog.controller.login;

import com.sise.blog.service.TypeService;
import com.sise.common.entity.Result;
import com.sise.common.pojo.Type;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 分类控制层
 * @Author: xzw
 * @Date: 2023/1/12 23:34
 */
@Api(value = "后台分类管理模块", description = "后台分类管理模块")
@RequestMapping("/type")
@RestController
@CrossOrigin
public class TypeController {
    @Autowired
    private TypeService typeService;

    //获取添加博客列表渲染
    @ApiOperation(value = "获取分类列表", notes = "返回分类列表数据")
    @GetMapping("/getTypeList")
    public Result getTypeList() {
        List<Type> typeList = typeService.getTypeList();
        return Result.ok("获取分类列表成功", typeList);
    }

}
