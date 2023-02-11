package com.sise.common.vo;

import lombok.Data;

/**
 * @Description: 用户后台统计返回VO
 * @Author: xzw
 * @Date: 2023/2/10 22:46
 */
@Data
public class BlogDataVO {
    /**
     * 标签名
     */
    private String labelName;
    /**
     * 对应的数量
     */
    private Integer count;
}
