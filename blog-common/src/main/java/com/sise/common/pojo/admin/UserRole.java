package com.sise.common.pojo.admin;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

/**
 * @Description: 用户角色实体类
 * @Author: xzw
 * @Date: 2023/1/2 0:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRole extends Model<UserRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 角色id
     */
    private Integer rid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
