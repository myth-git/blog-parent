package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.sise.blog.dao.mapper.RoleDao;
import com.sise.blog.dao.mapper.RoleMenuDao;
import com.sise.blog.dao.mapper.UserDao;
import com.sise.blog.dao.mapper.UserRoleDao;
import com.sise.blog.service.RoleMenuService;
import com.sise.blog.service.RoleResourceService;
import com.sise.blog.service.RoleService;
import com.sise.blog.service.UserRoleService;
import com.sise.common.constant.CommonConst;
import com.sise.common.dto.RoleDTO;
import com.sise.common.exception.BusinessException;
import com.sise.common.pojo.User;
import com.sise.common.pojo.admin.Role;
import com.sise.common.pojo.admin.RoleMenu;
import com.sise.common.pojo.admin.RoleResource;
import com.sise.common.pojo.admin.UserRole;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.RoleVO;
import com.sise.common.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleResourceService roleResourceService;

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

    @Override
    public Page<RoleDTO> listRole(QueryPageVO queryPageVO) {
        Page<RoleDTO> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        page.setTotal(roleDao.selectCount(new LambdaQueryWrapper<Role>()
                            .like(queryPageVO.getQueryString() != null, Role::getRoleName, queryPageVO.getQueryString())));
        page.setRecords(roleDao.listRole(queryPageVO));
        return page;
    }

    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        Role selectOne = roleDao.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, roleVO.getRoleName()));
        if (Objects.nonNull(selectOne) && !selectOne.getRid().equals(roleVO.getRid())){
            throw new BusinessException("角色已存在");
        }
        // 保存或更新角色信息
        Role role = Role.builder()
                .rid(roleVO.getRid())
                .roleName(roleVO.getRoleName())
                .roleLabel(roleVO.getRoleLabel())
                .isDisable(CommonConst.FALSE).build();
        this.saveOrUpdate(role);

        if (roleVO.getRid() != null){//代表更新，删除角色资源关联表的数据
            roleResourceService.remove(new LambdaQueryWrapper<RoleResource>().eq(RoleResource::getRid, roleVO.getRid()));
        }
        // 代表是保存或更新资源
        if (CollectionUtils.isNotEmpty(roleVO.getResourceIdList())) {
            List<RoleResource> roleResourceList = roleVO.getResourceIdList().stream().map(item ->
                    RoleResource.builder()
                            .rid(role.getRid())
                            .resourceId(item)
                            .build()
            ).collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
        }
        // 如果是新增的角色，则rid为空，用户菜单不为空
        if (roleVO.getRid() != null && CollectionUtils.isNotEmpty(roleVO.getMenuIdList())){
            roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRid, roleVO.getRid()));
        }
        if (CollectionUtils.isNotEmpty(roleVO.getMenuIdList())) {
            List<RoleMenu> roleMenuList = roleVO.getMenuIdList().stream()
                    .map(t -> RoleMenu.builder()
                            .rid(role.getRid())
                            .menuId(t)
                            .build()

                    ).collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
    }

    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        roleDao.deleteBatchIds(roleIdList);
        roleResourceService.remove(new LambdaQueryWrapper<RoleResource>().in(RoleResource::getRid,roleIdList));
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRid, roleIdList));
    }
}
