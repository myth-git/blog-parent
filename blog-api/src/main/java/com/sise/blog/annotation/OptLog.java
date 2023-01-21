package com.sise.blog.annotation;

import java.lang.annotation.*;

/**
 * @Description: 操作日志注解
 * @Author: xzw
 * @Date: 2023/1/21 22:08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    /**
     * @return 操作类型
     */
    String optType() default "";

}