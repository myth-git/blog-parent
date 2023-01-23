package com.sise.common.pojo.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/23 21:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "角色资源表", description = "角色资源表实体")
@EqualsAndHashCode(callSuper = false)
@TableName("blog_role_resource")
public class RoleResource extends Model<RoleResource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 权限id
     */
    private Integer resourceId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

