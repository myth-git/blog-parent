package com.sise.blog.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sise.blog.dao.pojo.SysUser;
import com.sise.blog.service.LoginService;
import com.sise.blog.utils.UserThreadLocal;
import com.sise.blog.vo.ErrorCode;
import com.sise.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    /**
     * 1、需要判断 请求的接口路径 是否为 HandlerMethod（controller方法）
     * 2、判断token是否为空，如果为空 未登录
     * 3、如果token不为空，登录验证 loginService checkToken
     * 4、如果认证成功 放行即可
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            //如果不是controller层放行，可能是 RequestResourceHandler
            //springboot程序 访问静态资源 默认去classpath下的static目录查询
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        //判断token是否为空
        if (StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            //告诉浏览器是json形式
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            //不放行
            return false;
        }
        //不为空，判断是否有这个用户
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            //告诉浏览器是json形式
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            //不放行
            return false;
        }
        //验证成功，放行
        //希望在controller中，直接获取用户的信息 怎么获取？
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除ThreadLocal中用完的信息，会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
