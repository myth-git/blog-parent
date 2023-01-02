package com.sise.blog.annotation;

import java.lang.annotation.*;

/**
 * @Description: 在需要登录验证的Controller的方法上使用此注解
 * @Author: xzw
 * @Date: 2022/12/31 22:48
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
}
