package com.sise.blog.common.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 */
//放在方法上
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";
}
