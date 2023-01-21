package com.sise.common.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sise.common.utils.JsonLongSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: 留言实体
 * @Author: xzw
 * @Date: 2023/1/19 11:50
 */
@Data
@ApiModel(value = "留言实体", description = "留言实体")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    /**
     * 留言id
     */
    @JsonSerialize(using = JsonLongSerializer.class)
    @TableId(value = "mid")
    private Long mid;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 弹幕过屏时间
     */
    private String time;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * ip地址
     */
    private String ip;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @Override
    protected Serializable pkVal() {
        return this.mid;
    }

}
