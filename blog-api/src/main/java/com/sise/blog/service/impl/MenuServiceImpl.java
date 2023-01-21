package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.sise.blog.dao.mapper.MenuDao;
import com.sise.blog.dao.mapper.RoleMenuDao;
import com.sise.blog.dao.mapper.UserRoleDao;
import com.sise.blog.service.MenuService;
import com.sise.blog.utils.BeanCopyUtils;
import com.sise.common.constant.CommonConst;
import com.sise.common.dto.UserMenuDTO;
import com.sise.common.pojo.admin.Menu;
import com.sise.common.pojo.admin.RoleMenu;
import com.sise.common.pojo.admin.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/8 9:22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public List<UserMenuDTO> listUserMenus(Long id) {
        //USER_MENUS表示用户后台
        return listMenus(id, CommonConst.USER_MENUS);
    }
    @Cacheable(value = {"AdminMenus"}, key = "#root.methodName")
    @Override
    public List<UserMenuDTO> listAdminMenus(Long id) {
        //ADMIN_MENUS表示管理员后台
        return listMenus(id, CommonConst.ADMIN_MENUS);
    }

    private List<UserMenuDTO> listMenus(Long id, Integer type) {
        //根据用户id来获取角色对应id
        UserRole userRole = userRoleDao.selectOne(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUid, id));
        //根据角色id获取对应的菜单id
        List<Integer> menusIdList = roleMenuDao.selectList(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRid, userRole.getRid()))
                .stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        //根据菜单id查看菜单表
        List<Menu> menusList = menuDao.selectList(new LambdaQueryWrapper<Menu>().in(Menu::getId, menusIdList).eq(Menu::getType, type));
        //获取目录列表
        List<Menu> catalogList = getCatalogList(menusList);
        //获取目录的子目录列表
        Map<Integer, List<Menu>> childrenCatalogList = getChildrenCatalogList(menusList);
        //转换成前端的形式的格式目录
        return convertUserMenuList(catalogList, childrenCatalogList);
    }

    /**
     * 转换用户菜单格式
     *
     * @param catalogList         目录
     * @param childrenCatalogList 子目录
     * @return
     */
    private List<UserMenuDTO> convertUserMenuList(List<Menu> catalogList, Map<Integer, List<Menu>> childrenCatalogList) {
        List<UserMenuDTO> layout = catalogList.stream().map(catalog -> {
            UserMenuDTO userMenuDTO = new UserMenuDTO();
            List<UserMenuDTO> list = new ArrayList<>();
            //通过目录id判断是否有子目录
            List<Menu> childrenList = childrenCatalogList.get(catalog.getId());
            if (CollectionUtils.isNotEmpty(childrenList)) {
                //目录
                userMenuDTO = BeanCopyUtils.copyObject(catalog, UserMenuDTO.class);
                //子目录
                list = childrenList.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(children -> {
                            UserMenuDTO dto = BeanCopyUtils.copyObject(children, UserMenuDTO.class);
                            //设置显示
                            dto.setHidden(children.getIsHidden().equals(1));
                            return dto;
                        }).collect(Collectors.toList());
            } else {
                //一级菜单处理, 可以变通, 一般不会执行这段代码
                userMenuDTO.setPath(catalog.getPath());
                userMenuDTO.setComponent("Layout");
                list.add(UserMenuDTO.builder()
                        .path("")
                        .name(catalog.getName())
                        .icon(catalog.getIcon())
                        .component(catalog.getComponent())
                        .build());
//                //无子目录处理
//                userMenuDTO = BeanCopyUtils.copyObject(catalog, UserMenuDTO.class);
//                //子目录为空
//                list.add(null);
            }
            userMenuDTO.setHidden(catalog.getIsHidden().equals(1));
            userMenuDTO.setChildren(list);
            return userMenuDTO;
        }).collect(Collectors.toList());

        return layout;

    }

    /**
     * 获取目录列表
     *
     * @param menusList
     * @return
     */
    private List<Menu> getCatalogList(List<Menu> menusList) {
        List<Menu> collect = menusList.stream()
                .filter(i -> Objects.isNull(i.getParentId()))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * 获取目录列表的子目录
     *
     * @param menusList
     * @return
     */
    private Map<Integer, List<Menu>> getChildrenCatalogList(List<Menu> menusList) {
        Map<Integer, List<Menu>> collect = menusList.stream()
                .filter(i -> Objects.nonNull(i.getParentId()))
                .collect(Collectors.groupingBy(Menu::getParentId));
        return collect;
    }

}
