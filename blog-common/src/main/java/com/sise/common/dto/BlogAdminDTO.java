package com.sise.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sise.common.utils.JsonLongSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Description: 管理员博客信息返回数据
 * @Author: xzw
 * @Date: 2023/1/21 19:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogAdminDTO {

    /**
     * 博客id
     */
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long blogId;

    /**
     * 首图
     */
    private String firstPicture;

    /**
     * 发布状态
     */
    private boolean published;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 点赞数
     */
    private Integer thumbs;

    /**
     * 标题
     */
    private String title;

    /**
     * 版权状态
     */
    private Integer copyright;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 文章分类名
     */
    private String typeName;
}
