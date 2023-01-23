package com.sise.blog.controller.login;

import com.sise.blog.annotation.OptLog;
import com.sise.blog.service.RoleService;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.entity.Result;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.RoleVO;
import com.sise.common.vo.UserRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation(value = "管理员获取后台角色分页数据")
    @GetMapping("/admin/listRoles")
    public Result listRole(QueryPageVO queryPageVO) {
        return Result.ok("管理员获取后台角色分页数据成功", roleService.listRole(queryPageVO));
    }

    @OptLog(optType = OptTypeConst.SAVE_OR_UPDATE)
    @ApiOperation(value = "管理角色保存或更新角色信息")
    @PostMapping("/admin/saveOrUpdateRole")
    public Result saveOrUpdateRole(@RequestBody @Valid RoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return Result.ok("管理角色保存或更新角色信息成功");
    }

    @OptLog(optType = OptTypeConst.REMOVE)
    @ApiOperation(value = "管理员删除角色")
    @DeleteMapping("/admin")
    public Result deleteRoles(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return Result.ok("管理员删除角色成功");
    }
}
