package com.sise.blog.exception;

import com.sise.blog.enums.StatusCodeEnum;
import lombok.Getter;

import static com.sise.blog.enums.StatusCodeEnum.FAIL;

/*
* 处理业务异常
* */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, StatusCodeEnum statusCodeEnum) {
        this.message = message;
        this.code = statusCodeEnum.getCode();
    }

    public BusinessException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }
}
