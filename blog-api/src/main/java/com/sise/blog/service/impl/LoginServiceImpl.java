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
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional //事务回滚
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
        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        //判断token是否为空
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        //判断token解析是否成功
        if (stringObjectMap == null) {
            return null;
        }
        //判断redis的token是否为空
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        //将json数据解析为对象
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        //删除掉redis中的token
        redisTemplate.delete(token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams params) {
        /**
         * 1、判断参数合法性
         * 2、查询数据库是否有该用户
         * 3、有的报错
         * 4、没有的话注册成功
         * 5、注册成功后返回一个token
         * 6、将token保存到redis中
         * 7、注意 加上事务，一旦中间的任务过程出现问题，注册用户 需要回滚
         */
        String account = params.getAccount();
        String nickname = params.getNickname();
        String password = params.getPassword();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(nickname)
                || StringUtils.isBlank(password)
        ) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), "用户名或昵称或密码不能为空");
        }
        SysUser sysUser = sysUserService.findAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1);//1 为true
        sysUser.setDeleted(0);// 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1,TimeUnit.DAYS);
        return Result.success(token);
    }
}
