package com.sise.blog.controller;

import com.sise.blog.service.LoginService;
import com.sise.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logout")
@Api(value = "退出登录模块", description = "退出登录模块的接口信息")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    @ApiOperation(value = "退出登录")
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
