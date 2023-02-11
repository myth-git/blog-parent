package com.sise.blog.controller;

import com.sise.blog.service.OssService;
import com.sise.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.UUID;

/**
 * @Description: 文件上传控制类
 * @Author: xzw
 * @Date: 2023/2/11 20:16
 */
@Api("文件上传")
@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping("/userAvatar")
    @ApiOperation(value = "用户上传头像")
    public Result userAvatar(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        HashMap<String, String> map = new HashMap<>();
        map.put("url", url);
        return Result.ok("上传成功", map);
    }

}
