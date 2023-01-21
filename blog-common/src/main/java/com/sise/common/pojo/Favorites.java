package com.sise.common.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sise.common.utils.JsonLongSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Description: 收藏量实体类
 * @Author: xzw
 * @Date: 2023/1/21 22:30
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Favorites extends Model<Favorites> {

    private static final long serialVersionUID = 1L;

    /**
     * 博客id
     */
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long blogId;

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
     * 用户id
     */
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long uid;

}
