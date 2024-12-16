package com.cottonusa.backend.config;

import org.springframework.context.annotation.Bean;
import utility.JwtUtil;

public class JwtConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(); // Giả sử JwtUtil có một constructor mặc định
    }
}
