package com.sise.common.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sise.common.utils.JsonLongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Description: 博客实体
 * @Author: xzw
 * @Date: 2022/12/31 0:21
 */
@Data
@ApiModel(value = "博客实体", description = "博客实体")
@EqualsAndHashCode(callSuper = false)
public class Articles extends Model<Articles> {
    private static final long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @JsonSerialize(using = JsonLongSerializer.class)
    @TableId(value = "id")
    private Long id;

    /**
     * 赞赏状态
     */
    private boolean appreciation;

    /**
     * 评论状态
     */
    private boolean commentAble;

    /**
     * 版权状态
     */
    private Integer copyright;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 首图
     */
    private String firstPicture;

    /**
     * 点赞数
     */
    private Integer thumbs;

    /**
     * 发布状态
     */
    private boolean published;

    /**
     * 推荐状态
     */
    private boolean recommend;

    /**
     * 标题
     */
    private String title;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField("`update_time`")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /**
     * 浏览次数
     */
    private Integer views;

    private Integer typeId;
    /*
    * 用户id
    * */
    private Long userId;

    /**
     * 博客摘要
     */
    private String description;

}
