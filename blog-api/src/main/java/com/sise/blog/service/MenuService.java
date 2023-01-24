package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.dto.LabelOptionDTO;
import com.sise.common.dto.MenuDTO;
import com.sise.common.dto.UserMenuDTO;
import com.sise.common.pojo.admin.Menu;
import com.sise.common.vo.MenuVO;
import com.sise.common.vo.QueryPageVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/8 9:22
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据用户id获取对应的用户后台菜单
     * @param id
     * @return
     */
    List<UserMenuDTO> listUserMenus(Long id);

    /**
     * 获取管理员菜单列表
     * @param id
     * @return
     */
    List<UserMenuDTO> listAdminMenus(Long id);

    /**
     * 角色管理查看角色菜单选项
     * @return
     */
    List<LabelOptionDTO> listMenuOptions();

    /**
     * 菜单管理获取后台菜单列表
     * @return
     */
    List<MenuDTO> listMenus(QueryPageVO queryPageVO);

    /**
     * 菜单管理新增或修改菜单
     * @param menuVO
     */
    void saveOrUpdateMenu(MenuVO menuVO);

    /**
     * 菜单管理删除菜单
     * @param menuId
     */
    void deleteMenu(Integer menuId);
}
