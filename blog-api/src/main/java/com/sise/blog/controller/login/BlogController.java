package com.sise.blog.controller.login;

import com.sise.blog.annotation.LoginRequired;
import com.sise.blog.service.ArticlesService;
import com.sise.blog.vo.Result;
import com.sise.common.pojo.User;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 个人后台博客管理
 * @Author: xzw
 * @Date: 2023/1/3 22:27
 */
@Api(value = "博客管理模块", description = "博客管理模块信息")
@RequestMapping("/blog")
@RestController
public class BlogController {

    @Autowired
    private ArticlesService articlesService;



    @LoginRequired
    @ApiOperation(value = "个人后台分页查询", notes = "返回分页数据")
    @PostMapping("/admin/findPage")
    public Result findPersonBlog(@RequestBody QueryPageVO queryPageVO, HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");//获取登录后的信息
        return Result.ok("获取分页数据成功", articlesService.findPersonBlog(queryPageVO, user.getId()));
    }
}
