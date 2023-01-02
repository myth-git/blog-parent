package com.sise.blog.annotation;

import java.lang.annotation.*;

/**
 * @Description: 限制一个设备的IP登录
 * @Author: xzw
 * @Date: 2022/12/31 22:51
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IpRequired {
}
