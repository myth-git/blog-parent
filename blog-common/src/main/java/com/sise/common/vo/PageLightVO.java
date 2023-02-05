package com.sise.common.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description: 高亮查询返回结果
 * @Author: xzw
 * @Date: 2023/2/4 20:51
 */
@Data
public class PageLightVO {
    private List<Map<String, Object>> records;

    private Integer total;
}
