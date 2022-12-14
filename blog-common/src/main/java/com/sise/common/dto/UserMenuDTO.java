package com.sise.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 用户菜单
 * @Author: xzw
 * @Date: 2023/1/8 9:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuDTO {
    /**
     * 菜单id
     */
    private Integer id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * icon
     */
    private String icon;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 子菜单列表
     */
    private List<UserMenuDTO> children;
}
