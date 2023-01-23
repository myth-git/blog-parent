package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.RoleResourceDao;
import com.sise.blog.service.RoleResourceService;
import com.sise.common.pojo.admin.RoleResource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/23 21:39
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceDao, RoleResource> implements RoleResourceService {
}
