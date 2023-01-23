package com.sise.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sise.common.utils.JsonLongSerializer;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Description: 用户禁用状态
 * @Author: xzw
 * @Date: 2023/1/23 10:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "用户禁用状态")
public class UserDisableVO {

    /**
     * 用户id
     */
    @JsonSerialize(using = JsonLongSerializer.class )
    @NotNull(message = "用户id不能为空")
    private Long uid;

    /**
     * 用户禁用状态
     */
    @NotNull(message = "用户禁用状态不能为空")
    private Integer status;

}
