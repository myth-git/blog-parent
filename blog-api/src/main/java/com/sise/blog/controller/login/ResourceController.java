package com.sise.blog.controller.login;

import com.sise.blog.annotation.OptLog;
import com.sise.blog.service.ResourceService;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.entity.Result;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.ResourceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/6 0:07
 */
@Api(value = "资源管理模块", description = "资源管理模块的接口信息")
@RequestMapping("/resource")
@RestController
@CrossOrigin
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 查看用户所有的资源
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询所有资源")
    public List<String> getResourceList() {
        return resourceService.getResourceList();
    }
    /**
     * 根据id查看用户的权限
     */
    @GetMapping("/getUserResource")
    @ApiOperation(value = "查询用户权限")
    public List<String> getUserResource(String id) {
        return resourceService.getUserResource(Long.parseLong(id));
    }

    @ApiOperation(value = "角色管理查看角色菜单选项")
    @GetMapping("/role")
    public Result listResourceOptions() {
        return Result.ok("查看角色菜单选项成功", resourceService.listResourceOptions());
    }

    @ApiOperation(value = "接口管理查看资源列表")
    @GetMapping("/listResources")
    public Result listResources(QueryPageVO queryPageVO){
        return Result.ok("查看资源列表成功", resourceService.listResources(queryPageVO));
    }

    @OptLog(optType = OptTypeConst.SAVE_OR_UPDATE)
    @ApiOperation(value = "接口管理新增或修改资源")
    @PostMapping("/admin/saveOrUpdateResource")
    public Result saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.ok("接口管理新增或修改资源成功");
    }

    @OptLog(optType = OptTypeConst.REMOVE)
    @ApiOperation(value = "接口管理删除资源")
    @DeleteMapping("/admin/delete/{resourceId}")
    public Result deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.ok("接口管理删除资源成功");
    }
}
