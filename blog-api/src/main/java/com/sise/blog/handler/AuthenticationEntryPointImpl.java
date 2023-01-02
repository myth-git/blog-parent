package com.sise.blog.handler;

import com.alibaba.fastjson.JSON;
import com.sise.blog.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.sise.common.enums.StatusCodeEnum.FAIL;

/**
 * @Description: 用户未登录处理
 * @Author: xzw
 * @Date: 2022/12/31 20:39
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(new Result(false,FAIL.getCode() ,"该用户未登录")));
    }
}
