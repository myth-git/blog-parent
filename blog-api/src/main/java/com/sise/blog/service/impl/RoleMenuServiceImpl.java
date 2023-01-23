package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.RoleMenuDao;
import com.sise.blog.service.RoleMenuService;
import com.sise.common.pojo.admin.RoleMenu;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/23 21:24
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements RoleMenuService {
}
