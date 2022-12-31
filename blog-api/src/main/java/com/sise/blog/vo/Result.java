package com.sise.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.sise.common.enums.StatusCodeEnum.FAIL;
import static com.sise.common.enums.StatusCodeEnum.SUCCESS;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

//    private boolean success;
//
//    private Integer code;
//
//    private String msg;
//
//    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }
//
    public static Result fail(Integer code, String msg) {
        return new Result(false, code, msg, null);
    }

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
        return restResult(true, null, SUCCESS.getCode(), message);
    }

    public static Result ok(String message, Object data) {
        return restResult(true, data, SUCCESS.getCode(), message);
    }

    public static Result fail(String message) {
        return restResult(false, null, FAIL.getCode(), message);
    }

    public static Result fail(String message, Integer code) {
        return restResult(false, null, code, message);
    }

    public static Result fail(String message, Object data) {
        return restResult(false, data, FAIL.getCode(), message);
    }

    private static Result restResult(Boolean flag, String message) {
        Result apiResult = new Result();
        apiResult.setFlag(flag);
        apiResult.setCode(flag ? SUCCESS.getCode() : FAIL.getCode());
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
