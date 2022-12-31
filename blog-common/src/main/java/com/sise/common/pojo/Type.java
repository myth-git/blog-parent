package com.sise.common.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

/**
 * @Description: 分类实体
 * @Author: xzw
 * @Date: 2022/12/31 10:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分类实体", description = "分类实体")
@EqualsAndHashCode(callSuper = false)
public class Type extends Model<Type> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String typeName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
