package com.sise.common.vo;

import com.sise.common.pojo.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 查询分类数据的返回值
 * @Author: xzw
 * @Date: 2022/12/31 10:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TypeVO extends Type implements Serializable {
    /*
    * 一个分类有多少篇文章
    * */
    private Integer typeCount;
}
