package com.sise.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 角色分类DTO
 * @Author: xzw
 * @Date: 2023/1/23 17:19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色标签
     */
    private String roleLabel;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否禁用
     */
    private Integer isDisable;

    /**
     * 资源id列表
     */
    private List<Integer> resourceIdList;

    /**
     * 菜单id列表
     */
    private List<Integer> menuIdList;

}