package com.sise.blog.controller.login;

import com.sise.blog.annotation.OptLog;
import com.sise.blog.service.LabelService;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.entity.Result;
import com.sise.common.pojo.Label;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 标签控制层
 * @Author: xzw
 * @Date: 2023/1/12 23:42
 */
@Api(value = "后台标签管理模块", description = "标签管理模块的接口信息")
@RequestMapping("/tag")
@RestController
@CrossOrigin
public class TagController {

    @Autowired
    private LabelService labelService;

    //获取添加博客标签回显
    @ApiOperation(value = "获取标签列表", notes = "返回标签列表数据")
    @GetMapping("/getTagList")
    public Result getTagList() {
        List<Label> labelList = labelService.getTagList();
        return Result.ok("获取标签列表成功", labelList);
    }

    @ApiOperation(value = "管理员后台标签数据", notes = "返回分页数据")
    @PostMapping("/admin/tagList")
    public Result adminTag(@RequestBody QueryPageVO queryPageVO) {
        return Result.ok("获取管理员后台标签数据成功", labelService.getAdminTag(queryPageVO));
    }

    @OptLog(optType = OptTypeConst.REMOVE)
    @DeleteMapping("/admin/delete")
    @ApiOperation(value = "管理员后台删除标签", notes = "删除标签")
    public Result delete(@RequestBody List<Integer> tagIdList) {
        labelService.delete(tagIdList);
        return Result.ok("管理员后台删除标签成功");
    }

    @OptLog(optType = OptTypeConst.SAVE_OR_UPDATE)
    @PostMapping("/admin/saveOrUpdate")
    @ApiOperation(value = "管理员后台标签添加或更改", notes = "添加或更改")
    public Result saveOrUpdateTag(@RequestBody Label label) {
        boolean flag = labelService.saveOrUpdateTag(label);
        if (flag) {
            return Result.ok("管理员标签添加或更改成功");
        } else {
            return Result.fail("管理员标签添加或更改失败（已经存在该标签了）");
        }
    }
}
