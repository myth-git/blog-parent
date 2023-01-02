package com.sise.blog.controller.login;

import com.sise.blog.service.UserService;
import com.sise.blog.vo.Result;
import com.sise.common.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户注册模块
 * @Author: xzw
 * @Date: 2022/12/31 11:52
 */
@RestController
@Api(value = "用户注册模块", description = "用户注册模块")
@RequestMapping("register")
public class RegisterUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ApiOperation(value = "用户注册", notes = "用户注册")
    public Result register(User user) {
        if (userService.add(user)){
            return Result.ok("注册成功");
        } else {
            return Result.fail("用户名已被注册");
        }
    }


}
