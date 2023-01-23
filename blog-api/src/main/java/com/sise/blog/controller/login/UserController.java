package com.sise.blog.controller.login;

import com.sise.blog.annotation.OptLog;
import com.sise.blog.service.RoleService;
import com.sise.blog.service.UserService;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.entity.Result;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.UserDisableVO;
import com.sise.common.vo.UserRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @ApiOperation(value = "获取管理员后台用户列表", notes = "获取后台用户列表")
    @GetMapping("/admin/userList")
    public Result adminUser(QueryPageVO queryPageVO) {
        return Result.ok("获取管理员后台用户列表成功", userService.getAdminUser(queryPageVO));
    }

    @OptLog(optType = OptTypeConst.UPDATE)
    @ApiOperation(value = "修改用户禁用状态")
    @PutMapping("/admin/disable")
    public Result updateUserDisable(@Valid @RequestBody UserDisableVO userDisableVO) {
        userService.updateUserDisable(userDisableVO);
        return Result.ok("修改用户禁用状态成功");
    }


}
