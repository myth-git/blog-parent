package com.sise.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 浏览量统计
 * @Author: xzw
 * @Date: 2023/1/19 11:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewsDTO {

    /**
     * 日期
     */
    private String day;

    /**
     * 访问量综合（每天）
     */
    private Integer viewsCount;

}
