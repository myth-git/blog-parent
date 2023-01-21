package com.sise.blog.controller.login;

import com.sise.blog.annotation.LoginRequired;
import com.sise.blog.service.MenuService;
import com.sise.common.entity.Result;
import com.sise.common.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 用户菜单模块
 * @Author: xzw
 * @Date: 2023/1/8 9:18
 */
@Api(value = "菜单模块", description = "菜单模块的接口信息")
@RequestMapping("/menu")
@RestController
@CrossOrigin
public class MenuController {

    @Autowired
    private MenuService menuService;

    @LoginRequired
    @GetMapping("/admin/listUserMenus")
    @ApiOperation(value = "获取用户菜单列表")
    public Result listUserMenus(HttpServletRequest request) {
        System.out.println("========================是不是啊==============");
        User user = (User) request.getAttribute("currentUser");
        System.out.println("user=========" + user);
        return Result.ok("获取用户菜单列表成功", menuService.listUserMenus(user.getId()));
//        return Result.ok("获取用户菜单列表成功", menuService.listUserMenus(1523893866560778242L));
    }

    @LoginRequired
    @GetMapping("/admin/listAdminMenus")
    @ApiOperation(value = "获取管理员菜单列表")
    public Result listAdminMenus(HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        return Result.ok("获取管理员菜单列表成功", menuService.listAdminMenus(user.getId()));
    }

}
