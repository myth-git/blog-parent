package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.dto.UserMenuDTO;
import com.sise.common.pojo.admin.Menu;

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
}
