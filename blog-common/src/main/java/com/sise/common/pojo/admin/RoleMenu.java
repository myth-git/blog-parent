package com.sise.common.pojo.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 角色菜单
 * @Author: xzw
 * @Date: 2023/1/8 9:44
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 菜单id
     */
    private Integer menuId;

}
