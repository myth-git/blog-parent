package com.sise.blog.controller;

import com.sise.blog.dao.pojo.SysUser;
import com.sise.blog.utils.UserThreadLocal;
import com.sise.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Api(value = "测试模块", description = "测试模块的接口信息")
public class TestController {

    @RequestMapping
    @ApiOperation(value = "测试")
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}