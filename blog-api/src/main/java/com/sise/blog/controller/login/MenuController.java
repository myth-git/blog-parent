package com.sise.blog.controller.login;

import com.sise.blog.annotation.LoginRequired;
import com.sise.blog.annotation.OptLog;
import com.sise.blog.service.MenuService;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.entity.Result;
import com.sise.common.pojo.User;
import com.sise.common.vo.MenuVO;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @ApiOperation(value = "角色管理查看角色菜单选项")
    @GetMapping("/admin/role")
    public Result listMenuOptions() {
        return Result.ok("查看角色菜单选项成功", menuService.listMenuOptions());
    }

    @GetMapping("/admin/listMenus")
    @ApiOperation(value = "菜单管理获取后台菜单列表")
    public Result listMenus(QueryPageVO queryPageVO) {
        return Result.ok("菜单管理获取后台菜单列表成功", menuService.listMenus(queryPageVO));
    }

    @OptLog(optType = OptTypeConst.SAVE_OR_UPDATE)
    @ApiOperation(value = "菜单管理新增或修改菜单")
    @PostMapping("/admin/saveOrUpdateMenu")
    public Result saveOrUpdateMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return Result.ok("新增或修改菜单成功");
    }

    @OptLog(optType = OptTypeConst.REMOVE)
    @ApiOperation(value = "菜单管理删除菜单")
    @DeleteMapping("/admin/delete/{menuId}")
    public Result deleteMenu(@PathVariable("menuId") Integer menuId){
        menuService.deleteMenu(menuId);
        return Result.ok("删除菜单成功");
    }

}
