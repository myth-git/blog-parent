package com.sise.common.vo;

import lombok.Data;

/**
 * @Description: 封装查询条件
 * @Author: xzw
 * @Date: 2022/12/31 0:29
 */
@Data
public class QueryPageVO {
    private Integer currentPage;    //页码
    private Integer pageSize;   //每页记录数
    private String queryString; //查询条件

    private Integer typeId; //分类id
    private Integer labelId; //标签id
    /**
     * 版权状态
     */
    private Integer copyright;

}
