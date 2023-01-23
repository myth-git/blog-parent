package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.dto.RoleDTO;
import com.sise.common.pojo.admin.Role;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.RoleVO;
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

    /**
     * 管理员获取后台角色分页数据
     * @param queryPageVO
     * @return
     */
    Page<RoleDTO> listRole(QueryPageVO queryPageVO);

    void saveOrUpdateRole(RoleVO roleVO);

    /**
     * 管理员删除角色
     * @param roleIdList
     */
    void deleteRoles(List<Integer> roleIdList);
}
