package com.sise.blog.service;

import com.sise.blog.dao.pojo.SysUser;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);
}
