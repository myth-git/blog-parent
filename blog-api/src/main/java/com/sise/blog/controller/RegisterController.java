package com.sise.blog.controller;

import com.sise.blog.service.LoginService;
import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.LoginParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
@Api(value = "注册模块", description = "注册模块的接口信息")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    @ApiOperation(value = "注册")
    public Result register(@RequestBody LoginParams params){
        return loginService.register(params);
    }
}
