package com.sise.blog.handler;

import com.alibaba.fastjson.JSON;
import com.sise.blog.vo.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.sise.common.enums.StatusCodeEnum.SUCCESS;



/**
 * @Description: 注销成功处理
 * @Author: xzw
 * @Date: 2022/12/31 20:49
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(new Result(true,  SUCCESS.getCode(),"注销成功")));
    }
}
