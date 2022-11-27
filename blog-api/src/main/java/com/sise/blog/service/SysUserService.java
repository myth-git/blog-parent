package com.sise.blog.service;

import com.sise.blog.dao.pojo.SysUser;
import com.sise.blog.vo.Result;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 查找是否有相同的用户名
     * @param account
     * @return
     */
    SysUser findAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
