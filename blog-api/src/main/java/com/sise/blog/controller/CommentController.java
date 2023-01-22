package com.sise.blog.controller;

import com.sise.blog.annotation.LoginRequired;

import com.sise.blog.annotation.OptLog;
import com.sise.blog.service.CommentService;
import com.sise.blog.vo.CommentVo;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.entity.Result;
import com.sise.common.pojo.Comment;
import com.sise.common.pojo.User;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 评论模块
 * @Author: xzw
 * @Date: 2023/1/18 14:53
 */
@Api(value = "评论模块", description = "评论模块")
@RequestMapping("/comment")
@RestController
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "根据blogId查找评论")
    @GetMapping("/commentList/{id}")
    public Result getCommentList(@PathVariable("id") Long id) {
        return Result.ok("获取评论列表成功", commentService.getCommentList(id));
    }

    @ApiOperation(value = "回复评论")
    @LoginRequired
    @PostMapping("/admin/replyComment")
    public Result replyComment(@RequestBody Comment comment, HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        commentService.replyComment(comment, user.getId());
        return Result.ok("回复评论成功");
    }

    @LoginRequired
    @ApiOperation(value = "用户删除评论")
    @DeleteMapping("/admin/delComment/{blogId}/{commentId}")
    public Result delComment(@PathVariable Long blogId, @PathVariable Long commentId, HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        if (commentService.deleteComments(blogId, commentId, user.getId())) {
            return Result.ok("删除评论成功");
        } else {
            return Result.fail("该评论不是你发的，你无权删除！！");
        }
    }

    @ApiOperation(value = "获取管理员后台的评论分页数据")
    @PostMapping("/admin/commentPage")
    public Result adminComments(@RequestBody QueryPageVO queryPageVO){
        return Result.ok("获取管理员后台的评论分页数据", commentService.adminComments(queryPageVO));
    }

    @OptLog(optType = OptTypeConst.REMOVE)
    @ApiOperation(value = "管理员删除评论")
    @DeleteMapping("/admin/delete")
    public Result adminDelComment(@RequestBody List<Long> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.ok("管理员删除评论成功");
    }
}
