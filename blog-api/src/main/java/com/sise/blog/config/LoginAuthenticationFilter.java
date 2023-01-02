package com.sise.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sise.blog.dto.UserDetailDTO;
import com.sise.blog.service.UserService;
import com.sise.blog.utils.IpUtil;
import com.sise.blog.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 自定义前后端分离认证Security登录认证过滤器
 * 否则login默认只能接受表单请求，无法接受json
 * @Author: xzw
 * @Date: 2022/12/31 16:30
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionRegistry sessionRegistry;

    //获取用户的表单改成json格式获取
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //判断是否是post请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //判断是否是json格式请求类型
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            //从接送数据中获取用户输入的用户名和密码进行校验
            try {
                Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                String username = userInfo.get(getUsernameParameter());
                String password = userInfo.get(getPasswordParameter());
                String code = userInfo.get("code");
                String verKey = userInfo.get("verKey");
                //创建登录信息
                UserDetailDTO userDetailDTO = null;
                //获取用户ip信息
                String ipAddress = IpUtil.getIpAddr(request);
                String ipSource = IpUtil.getIpSource(ipAddress);
                //验证码验证
                if (verKey.length() != 0) {
                    userService.verifyCode(verKey, code);
                }
                //获取用户信息
                if (Objects.nonNull(username)) {
                    //要返回userDetailDTO数据啊 找了那么久
                    userDetailDTO = userService.getUserDetail(username, request, ipAddress, ipSource);
                }
                //查看用户是否禁用
                if (!userDetailDTO.isStatus()) {
                    throw new AuthenticationServiceException("该用户被禁用了！！");
                }
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                // 将登录信息放入springSecurity管理
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetailDTO, null, userDetailDTO.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                // 用户名密码验证通过后，将含有用户信息的token注册session
                sessionRegistry.registerNewSession(request.getSession().getId(), auth.getPrincipal());
                this.setDetails(request, auth);
                try {
                    return this.getAuthenticationManager().authenticate(authRequest);
                }catch (BadCredentialsException e) {
                    throw new AuthenticationServiceException("请输入正确的用户名或密码");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.attemptAuthentication(request, response);
    }
}
