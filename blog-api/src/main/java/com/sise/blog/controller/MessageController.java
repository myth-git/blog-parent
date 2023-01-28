package com.sise.blog.controller;

import com.sise.blog.annotation.IpRequired;
import com.sise.blog.annotation.OptLog;
import com.sise.blog.service.MessageService;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.entity.Result;
import com.sise.common.pojo.Message;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/28 23:48
 */
@RestController
@CrossOrigin
@Api(value = "留言模块", description = "留言模块的接口信息")
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation("首页留言模块添加留言")
    @IpRequired
    @PostMapping("/add")
    public Result addMessage(@RequestBody Message message, HttpServletRequest request) {
        boolean flag = messageService.addMessage(message, (String) request.getAttribute("host"));
        if (flag) {
            return Result.ok("添加留言成功");
        } else {
            return Result.ok("添加留言失败");
        }
    }

    @ApiOperation("管理员后台获取留言分页信息")
    @PostMapping("/getMessagePage")
    public Result getMessagePage(@RequestBody QueryPageVO queryPageVO) {
        return Result.ok("后台获取留言分页信息", messageService.getMessagePage(queryPageVO));
    }

    @OptLog(optType = OptTypeConst.REMOVE)
    @ApiOperation("管理员后台删除留言")
    @DeleteMapping("/admin/delete")
    public Result deleteMessage(@RequestBody List<Long> messageIdList){
        messageService.deleteMessage(messageIdList);
        return Result.ok("删除留言成功");
    }

    @ApiOperation("首页留言模块获取留言列表")
    @GetMapping("/getMessageList")
    public Result getMessageList() {
        return Result.ok("获取留言列表成功", messageService.getMessageList());
    }

}
