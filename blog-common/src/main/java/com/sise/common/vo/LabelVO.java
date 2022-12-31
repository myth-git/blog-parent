package com.sise.common.vo;

import com.sise.common.pojo.Label;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description: 返回标签的数据
 * @Author: xzw
 * @Date: 2022/12/31 10:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LabelVO extends Label implements Serializable {
    /*
    * 一个标签有多少篇文章
    * */
    private Integer labelCount;
}
