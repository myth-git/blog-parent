package com.sise.blog.controller.login;

import com.sise.blog.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
