package com.sise.blog.controller.login;

import com.sise.blog.service.RoleService;
import com.sise.common.entity.Result;
import com.sise.common.vo.UserRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/23 15:56
 */
@Api(value = "角色模块", description = "角色模块的接口信息")
@RequestMapping("/role")
@RestController
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取后台全部角色数据")
    @GetMapping("/admin/listAllRoles")
    public Result listAllRoles() {
        return Result.ok("获取后台全部角色数据成功", roleService.listAllRoles());
    }

    @ApiOperation(value = "管理员修改用户角色")
    @PutMapping("/admin/user")
    public Result updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        roleService.updateUserRole(userRoleVO);
        return Result.ok("管理员修改用户角色成功");
    }

}
