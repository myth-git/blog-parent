package com.sise.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sise.blog.dao.pojo.SysUser;
import com.sise.blog.service.LoginService;
import com.sise.blog.service.SysUserService;
import com.sise.blog.utils.JWTUtils;
import com.sise.blog.vo.ErrorCode;
import com.sise.blog.vo.Result;
import com.sise.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    private static final String slat = "mszlu!@#";

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1、检查参数是否合法
         * 2、根据用户名和密码去user表中查询 是否存在
         * 3、如果不存在 登录失败
         * 4、如果存在 使用jwt 生成token 返回给前端
         * 5、token放入到redis中，redis token：user信息 设置过期时间
         * （登录认证的时候，先认证token字符串是否合法，然后去认证是否存在）
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //md5加密和加盐
        DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
