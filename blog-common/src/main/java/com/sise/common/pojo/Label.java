package com.sise.common.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

/**
 * @Description: 标签实体类
 * @Author: xzw
 * @Date: 2022/12/31 10:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "标签实体", description = "标签实体")
@EqualsAndHashCode(callSuper = false)
public class Label extends Model<Label> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String labelName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
