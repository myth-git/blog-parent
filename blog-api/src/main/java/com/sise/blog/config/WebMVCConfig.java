package com.sise.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置  允许8080端口访问本项目的8888端口 两个服务用统一端口会发生冲突问题
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }

}
