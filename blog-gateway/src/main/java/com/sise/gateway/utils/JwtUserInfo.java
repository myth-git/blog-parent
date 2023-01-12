package com.sise.gateway.utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sise.common.utils.JsonLongSerializer;
import lombok.Data;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/5 22:11
 */
@Data
public class JwtUserInfo {
    @JsonSerialize(using = JsonLongSerializer.class )
    private Long id;

    private String username;

    private String nickname;

    private Integer roleId;
}
