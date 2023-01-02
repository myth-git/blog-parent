package com.sise.blog.handler;

import com.alibaba.fastjson.JSON;
import com.sise.blog.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.sise.common.enums.StatusCodeEnum.FAIL;

/**
 * @Description: 登录失败处理
 * @Author: xzw
 * @Date: 2022/12/31 20:46
 */
@Component
public class AuthenticationFailHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(new Result(false , FAIL.getCode(), "请输入正确的用户名和密码" ,e.getMessage())));
        System.out.println("登录失败");
        e.printStackTrace();
    }
}
