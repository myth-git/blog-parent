package com.sise.common.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sise.common.utils.JsonLongSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/2/5 23:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO implements Serializable {

    @JsonSerialize(using = JsonLongSerializer.class )
    @TableId(value = "id")
    private Long id;

    private String nickname;

    private String username;

    private String password;

    private String email;

    private String avatar;

    private String code;

    /**
     * 登录类型
     */
    private Integer loginType;

}
