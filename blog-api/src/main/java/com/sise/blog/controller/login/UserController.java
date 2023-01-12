package com.sise.blog.controller.login;

import com.sise.blog.service.UserService;
import com.sise.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 后台用户信息模块
 * @Author: xzw
 * @Date: 2023/1/6 21:31
 */
@Api(value = "后台用户信息模块", description = "后台用户信息模块")
@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping("/getUserList")
    public Result getUserList() {
        return Result.ok("获取用户列表成功", userService.getUserList());
    }

}
