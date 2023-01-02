package com.sise.blog.handler;

import com.alibaba.fastjson.JSON;
import com.sise.blog.dao.mapper.UserDao;
import com.sise.blog.dto.UserLoginDTO;
import com.sise.blog.utils.*;
import com.sise.blog.vo.Result;
import com.sise.common.constant.RedisConst;
import com.sise.common.enums.StatusCodeEnum;
import com.sise.common.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Description: 登录成功处理
 * @Author: xzw
 * @Date: 2022/12/31 21:15
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Resource
    private UserDao userDao;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        //返回登录用户信息
        User user = BeanCopyUtils.copyObject(UserUtils.getLoginUser(), User.class);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        //认证成功，生成jwt
        try {
            HashMap<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(user.getId()));
            payload.put("lastIp", user.getLastIp());
            payload.put("username", user.getUsername());
            String token = JWTUtil.getToken(payload);
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            BeanUtils.copyProperties(user, userLoginDTO);
            userLoginDTO.setId(String.valueOf(user.getId()));
            HashMap<String, Object> userInfo = new HashMap<>();
            userInfo.put("token", token);
            userInfo.put("user", userLoginDTO);
            // token设置白名单，因此可以管理token的有效期
            redisUtil.set(RedisConst.TOKEN_ALLOW_LIST + user.getId(), token, RedisConst.HOUR);
            // 这里的JsonSerialize不起作用，所以要手动将Long类型的uid转换成String，否则会失去精度
            httpServletResponse.getWriter().write(JSON.toJSONString(new Result(true, StatusCodeEnum.SUCCESS.getCode(), "token生成成功", userInfo)));
        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.getWriter().write(JSON.toJSONString(new Result(false, StatusCodeEnum.FAIL.getCode(), "token生成失败")));
        }
        // 更新用户ip，最近登录时间
        updateUserInfo();
    }
    /**
     * 更新用户信息
     */
    @Async
    public void updateUserInfo() {
        User user = new User();
        user.setId(UserUtils.getLoginUser().getId());
        user.setLastIp(UserUtils.getLoginUser().getLastIp());
        user.setIpSource(UserUtils.getLoginUser().getIpSource());
        user.setStatus(true);
        user.setLastLoginTime(UserUtils.getLoginUser().getLastLoginTime());
        userDao.updateById(user);
    }
}
