package com.sise.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 文章统计
 * @Author: xzw
 * @Date: 2023/1/19 11:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogStatisticsDTO {

    /**
     * 日期
     */
    private String date;

    /**
     * 数量
     */
    private Integer count;

}