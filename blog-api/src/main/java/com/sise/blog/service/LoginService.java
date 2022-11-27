package com.sise.blog.service;

import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.LoginParams;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);
}
