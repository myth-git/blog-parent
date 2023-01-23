package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.RoleDao;
import com.sise.blog.dao.mapper.UserDao;
import com.sise.blog.dao.mapper.UserRoleDao;
import com.sise.blog.service.RoleService;
import com.sise.blog.service.UserRoleService;
import com.sise.common.pojo.User;
import com.sise.common.pojo.admin.Role;
import com.sise.common.pojo.admin.UserRole;
import com.sise.common.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/23 11:54
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void updateUserRole(UserRoleVO userRoleVO) {
        User user = User.builder()
                            .id(userRoleVO.getUid())
                            .nickname(userRoleVO.getNickname())
                            .status(true)
                            .build();
        userDao.updateById(user);

        //先删除用户所拥有的角色
        userRoleDao.delete(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUid, userRoleVO.getUid()));
        List<UserRole> userRoleList = userRoleVO.getRoleIdList().stream()
                .map(t -> UserRole.builder()
                        .rid(t)
                        .uid(userRoleVO.getUid())
                        .build()).collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<Role> listAllRoles() {
        List<Role> roleList = roleDao.selectList(new LambdaQueryWrapper<Role>()
                .select(Role::getRid, Role::getRoleName));
        return roleList;
    }
}
