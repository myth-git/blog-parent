package com.sise.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 文章排行
 * @Author: xzw
 * @Date: 2023/1/19 11:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogRankDTO {

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览量
     */
    private Integer views;


}
