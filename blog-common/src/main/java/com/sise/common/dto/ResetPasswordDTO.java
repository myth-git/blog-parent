package com.sise.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 找回密码参数
 * @Author: xzw
 * @Date: 2023/2/1 22:22
 */
@Data
public class ResetPasswordDTO implements Serializable {

    private String username;

    private String password;

    private String email;

    private String code; // 邮箱验证码
}
