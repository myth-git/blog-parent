package com.sise.blog.controller;

import com.sise.blog.dao.pojo.SysUser;
import com.sise.blog.utils.UserThreadLocal;
import com.sise.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}