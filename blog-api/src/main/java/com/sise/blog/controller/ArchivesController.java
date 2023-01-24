package com.sise.blog.controller;

import com.sise.blog.service.ArchivesService;
import com.sise.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/24 22:17
 */
@Api(value = "归档模块", description = "归档模块的接口信息")
@RequestMapping("/archives")
@RestController
@CrossOrigin
public class ArchivesController {

    @Autowired
    private ArchivesService archivesService;

    @GetMapping("/getArchivesList")
    @ApiOperation(value = "获取归档列表")
    public Result getArchivesList() {
        return Result.ok("获取归档列表成功", archivesService.getArchivesList());
    }
}
