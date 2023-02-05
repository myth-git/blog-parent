package com.sise.blog.controller.login;

import com.sise.blog.annotation.LoginRequired;
import com.sise.blog.annotation.OptLog;
import com.sise.blog.es.mq.PostMqIndexMessage;
import com.sise.blog.service.ArticlesService;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.constant.RabbitMQConst;
import com.sise.common.dto.AddBlogDTO;
import com.sise.common.entity.Result;
import com.sise.common.pojo.User;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @Autowired
    private AmqpTemplate amqpTemplate;


    @LoginRequired
    @ApiOperation(value = "个人后台分页查询", notes = "返回分页数据")
    @PostMapping("/admin/findPage")
    public Result findPersonBlog(@RequestBody QueryPageVO queryPageVO, HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");//获取登录后的信息
        return Result.ok("获取分页数据成功", articlesService.findPersonBlog(queryPageVO, user.getId()));
    }

    @LoginRequired
    @ApiOperation(value = "用户添加或更新博客")
    @PostMapping("/admin/addOrUpdate")
    @Transactional(rollbackFor = Exception.class)//要么全部执行成功，要么全部执行失败
    public Result addOrUpdate(@RequestBody AddBlogDTO addBlogDTO, HttpServletRequest request) {
        //获取登录后的用户信息
        User user = (User) request.getAttribute("currentUser");
        Long blogId = articlesService.addOrUpdate(addBlogDTO, user.getId());
        System.out.println("测试es" + blogId);
        //生产者发消息给mq，然后进行es同步
        amqpTemplate.convertAndSend(RabbitMQConst.esExchange, RabbitMQConst.esBingKey,
                new PostMqIndexMessage(blogId, PostMqIndexMessage.CREATE_OR_UPDATE));
        return Result.ok("用户添加或更新博客成功");
    }

    @LoginRequired
    @ApiOperation(value = "用户保存博客为草稿")
    @PostMapping("/admin/addDraft")
    public Result addDraft(@RequestBody AddBlogDTO addBlogDTO, HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        Long blogId = articlesService.addDraft(addBlogDTO, user.getId());
        return Result.ok("用户保存博客为草稿成功");
    }

    @ApiOperation(value = "首页获取根据id博客信息")
    @GetMapping("/{id}")
    public Result getBlogDetail(@PathVariable("id") Long id) {
        //更新阅读量
        articlesService.updateView(id);
        return Result.ok("获取博客信息成功", articlesService.getBlogDetail(id));
    }

    @ApiOperation(value = "后台编辑获取根据id博客信息")
    @GetMapping("/admin/{id}")
    public Result getAdminBlogDetail(@PathVariable("id") Long id) {
        return Result.ok("获取后台博客信息成功", articlesService.getAdminBlogDetail(id));
    }

    @ApiOperation(value = "查看管理员后台信息")
    @GetMapping("/admin/getBlogBackInfo")
    public Result getBlogBackInfo() {
        return Result.ok("查看后台管理员信息成功", articlesService.getBlogBackInfo());
    }

    @ApiOperation(value = "管理员后台获取博客信息")
    @GetMapping("/admin/blogPage")
    public Result adminBlogPage(QueryPageVO queryPageVO) {
        return Result.ok("获取管理员后台获取博客信息成功", articlesService.adminBlogPage(queryPageVO));
    }

    @OptLog(optType = OptTypeConst.REMOVE)
    @ApiOperation(value = "管理员删除博客")
    @DeleteMapping("/admin/delete")
    public Result deleteBlogs(@RequestBody List<Long> blogIdList) {
        articlesService.deleteBlogs(blogIdList);
//        PostMqIndexMessage postMqIndexMessage = new PostMqIndexMessage();
//        postMqIndexMessage.setBlogIdList(blogIdList).setType(PostMqIndexMessage.REMOVE);
//        amqpTemplate.convertAndSend(RabbitMQConst.esExchange, RabbitMQConst.esBingKey,
//                postMqIndexMessage);
        amqpTemplate.convertAndSend(RabbitMQConst.esExchange, RabbitMQConst.esBingKey,
                new PostMqIndexMessage(blogIdList, PostMqIndexMessage.REMOVE));
        return Result.ok("删除博客成功");
    }

    @ApiOperation(value = "点赞")
    @GetMapping("/admin/thumbUp/{blogId}/{uid}")
    public Result thumbsUp(@PathVariable("blogId") Long blogId, @PathVariable("uid") Long uid) {
        boolean flag = articlesService.thumbsUp(blogId, uid);
        if (flag){
            return Result.ok("点赞成功");
        } else {
            return Result.fail("取消点赞");
        }
    }

    @ApiOperation(value = "收藏")
    @GetMapping("/admin/favorite/{blogId}/{uid}")
    public Result favorite(@PathVariable("blogId") Long blogId, @PathVariable("uid") Long uid) {
        boolean flag = articlesService.favorite(blogId, uid);
        if (flag) {
            return Result.ok("收藏成功");
        } else {
            return Result.fail("取消收藏");
        }
    }

}
