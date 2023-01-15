package com.sise.common.dto;

import com.sise.common.pojo.Articles;
import lombok.Data;

/**
 * @Description: 添加博客参数
 * @Author: xzw
 * @Date: 2023/1/13 0:16
 */
@Data
public class AddBlogDTO extends Articles {
    /**
     * 存放的是博客对应的标签列表
     */
    private Integer[] value;
}
