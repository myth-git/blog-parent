package com.sise.common.entity;

import com.sise.common.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {
    private Boolean flag;
    private Integer code;
    private String message;
    private Object data;

    public Result(Boolean flag, Integer code, Object data) {
        this.flag = flag;
        this.code = code;
        this.data = data;
    }

    public static Result ok(String message) {
        return restResult(true, null, StatusCodeEnum.SUCCESS.getCode(), message);
    }

    public static Result ok(String message, Object data) {
        return restResult(true, data, StatusCodeEnum.SUCCESS.getCode(), message);
    }

    public static Result fail(String message) {
        return restResult(false, null, StatusCodeEnum.FAIL.getCode(), message);
    }

    public static Result fail(String message, Integer code) {
        return restResult(false, null, code, message);
    }

    public static Result fail(String message, Object data) {
        return restResult(false, data, StatusCodeEnum.FAIL.getCode(), message);
    }

    private static Result restResult(Boolean flag, String message) {
        Result apiResult = new Result();
        apiResult.setFlag(flag);
        apiResult.setCode(flag ? StatusCodeEnum.SUCCESS.getCode() : StatusCodeEnum.FAIL.getCode());
        apiResult.setMessage(message);
        return apiResult;
    }

    private static Result restResult(Boolean flag, Object data, Integer code, String message) {
        Result apiResult = new Result();
        apiResult.setFlag(flag);
        apiResult.setData(data);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        return apiResult;
    }

}
