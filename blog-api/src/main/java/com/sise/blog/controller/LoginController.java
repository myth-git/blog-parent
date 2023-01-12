//package com.sise.blog.controller;
//
//import com.sise.blog.service.LoginService;
//import com.sise.blog.vo.Result;
//import com.sise.blog.vo.params.LoginParams;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("login")
//@Api(value = "登录模块", description = "登录模块的接口信息")
//public class LoginController {
//
//    @Autowired
//    private LoginService loginService;
//
//    @PostMapping
//    @ApiOperation(value = "登录")
//    public Result login(@RequestBody LoginParams loginParams) {
//        return loginService.login(loginParams);
//    }
//}
