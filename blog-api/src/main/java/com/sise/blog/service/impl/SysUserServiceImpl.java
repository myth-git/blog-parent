package com.sise.blog.service.impl;

import com.sise.blog.dao.mapper.SysUserMapper;
import com.sise.blog.dao.pojo.SysUser;
import com.sise.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser.setNickname("空的脑瓜子");
        }
        return sysUser;
    }
}
