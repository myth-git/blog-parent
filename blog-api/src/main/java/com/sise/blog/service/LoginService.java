package com.sise.blog.service;

import com.sise.blog.dao.pojo.SysUser;
import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.LoginParams;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    /**
     * 校验token的合法性
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param params
     * @return
     */
    Result register(LoginParams params);
}
