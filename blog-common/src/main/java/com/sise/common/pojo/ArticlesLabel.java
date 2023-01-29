package com.sise.common.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * @Description: 多对多中间表，可以不写
 * @Author: xzw
 * @Date: 2022/12/31 11:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ArticlesLabel extends Model<ArticlesLabel> {
    private static final long serialVersionUID = 1L;

    private Long articlesId;

    private Long labelId;

    @Override
    protected Serializable pkVal() {
        return this.articlesId;
    }
}
