package com.sise.blog.controller.login;

import com.sise.blog.annotation.OptLog;
import com.sise.blog.service.TypeService;
import com.sise.common.constant.OptTypeConst;
import com.sise.common.entity.Result;
import com.sise.common.pojo.Type;
import com.sise.common.vo.QueryPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 分类控制层
 * @Author: xzw
 * @Date: 2023/1/12 23:34
 */
@Api(value = "后台分类管理模块", description = "后台分类管理模块")
@RequestMapping("/type")
@RestController
@CrossOrigin
public class TypeController {
    @Autowired
    private TypeService typeService;

    //获取添加博客列表渲染
    @ApiOperation(value = "获取分类列表", notes = "返回分类列表数据")
    @GetMapping("/getTypeList")
    public Result getTypeList() {
        List<Type> typeList = typeService.getTypeList();
        return Result.ok("获取分类列表成功", typeList);
    }

    @ApiOperation(value = "管理员后台分类数据", notes = "返回分页数据")
    @PostMapping("/admin/typeList")
    public Result adminType(@RequestBody QueryPageVO queryPageVO) {
        return Result.ok("获取管理员后台分类数据成功", typeService.getAdminType(queryPageVO));
    }

    @OptLog(optType = OptTypeConst.REMOVE)
    @DeleteMapping("/admin/delete")
    @ApiOperation(value = "管理员后台删除分类", notes = "删除")
    public Result delete(@RequestBody List<Integer> typeIdList) {
        typeService.delete(typeIdList);
        return Result.ok("管理员后台删除分类成功");
    }

    @OptLog(optType = OptTypeConst.SAVE_OR_UPDATE)
    @PostMapping("/admin/saveOrUpdate")
    @ApiOperation(value = "管理员后台分类添加或更改", notes = "添加或更改")
    public Result saveOrUpdateType(@RequestBody Type type) {
        boolean flag = typeService.saveOrUpdateType(type);
        if (flag) {
            return Result.ok("分类添加或更改成功");
        } else {
            return Result.fail("分类添加或更改失败");
        }
    }

}
