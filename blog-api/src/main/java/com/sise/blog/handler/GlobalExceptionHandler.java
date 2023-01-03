package com.sise.blog.handler;

import com.sise.blog.vo.Result;
import com.sise.common.exception.BusinessException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 异常处理器
 * @Author: xzw
 * @Date: 2023/1/3 20:44
 */
@Slf4j
@ControllerAdvice //全局异常
@Api(value = "全局异常处理模块", description = "全局异常处理的信息")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class) //抛出该异常才会执行此方法
    @ResponseBody //返回json格式
    public Result businessHandler(BusinessException b) {
        b.printStackTrace();  //在命令行打印异常信息在程序中出错的位置及原因
        //log.debug(b.getMessage());
        log.info(b.getMessage());
        System.out.println("异常处理");
        return Result.fail(b.getMessage(), b.getCode());
    }
}
