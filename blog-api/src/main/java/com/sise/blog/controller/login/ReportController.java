package com.sise.blog.controller.login;

import com.sise.blog.annotation.LoginRequired;
import com.sise.blog.service.ReportService;
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
 * @Description: 后台数据统计模块
 * @Author: xzw
 * @Date: 2023/1/15 22:35
 */
@Api(value = "后台数据统计模块", description = "后台数据统计模块")
@RequestMapping("/report")
@RestController
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @LoginRequired
    @GetMapping("/admin/getReport2")
    @ApiOperation(value = "获取单篇博文数据")
    public Result getReport2(HttpServletRequest request) throws Exception {
        User user = (User) request.getAttribute("currentUser");
        return Result.ok("获取数据统计成功", reportService.getReport2(user.getId()));
    }

}
