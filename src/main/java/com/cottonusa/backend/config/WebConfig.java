package com.cottonusa.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cho tất cả các endpoint
                .allowedOrigins("http://localhost:3000") // Nguồn gốc cho phép
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS") // Các phương thức cho phép
                .allowedHeaders("*") // Các header cho phép
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("Set-Cookie"); // Cho phép thông tin xác thực
    }
}