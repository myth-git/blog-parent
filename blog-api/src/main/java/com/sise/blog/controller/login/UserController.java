package com.sise.blog.controller.login;

import com.baomidou.mybatisplus.extension.api.R;
import com.sise.blog.annotation.OptLog;
import com.sise.blog.service.MsmService;
import com.sise.blog.service.RoleService;
import com.sise.blog.service.UserService;
import com.sise.blog.utils.RedisUtil;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.dto.ResetPasswordDTO;
import com.sise.common.dto.UpdateUserDTO;
import com.sise.common.entity.Result;
import com.sise.common.qq.RandomUtil;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.UserDisableVO;
import com.sise.common.vo.UserRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;

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
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisUtil redisUtil;

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


    @ApiOperation(value = "发送邮箱验证码", notes = "发送邮箱验证码")
    @GetMapping("/code")
    public Result sendEmailCode(String email) {
        msmService.sendEmail(email);
        return Result.ok("获取验证码成功");
    }

    @ApiOperation(value = "用户找回密码", notes = "用户找回密码")
    @PutMapping("/resetPassword")
    public Result resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        userService.resetPassword(resetPasswordDTO);
        return Result.ok("找回密码成功");
    }

    @ApiOperation(value = "用户更新个人信息", notes = "用户更新个人信息")
    @PutMapping("/admin/updateUser")
    public Result updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        boolean flag = userService.updateUser(updateUserDTO);
        if (flag){
            return Result.ok("用户更新个人信息成功");
        } else {
            return Result.fail("该用户名称已存在");
        }
    }


}
