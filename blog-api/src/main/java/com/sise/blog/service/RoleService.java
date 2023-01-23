package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.pojo.admin.Role;
import com.sise.common.vo.UserRoleVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/23 11:53
 */
public interface RoleService extends IService<Role> {
    /**
     * 管理员修改用户角色
     * @param userRoleVO
     */
    void updateUserRole(UserRoleVO userRoleVO);

    /**
     * 获取后台全部角色数据
     * @return
     */
    List<Role> listAllRoles();
}
