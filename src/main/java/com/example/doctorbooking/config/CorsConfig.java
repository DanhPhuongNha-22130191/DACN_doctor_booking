package com.example.doctorbooking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng cho toàn bộ đường dẫn API
                .allowedOrigins("*") // Cho phép mọi nguồn (domain) có thể gọi
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Mở mọi phương thức HTTP
                .allowedHeaders("*") // Chấp nhận mọi HTTP Header
                .allowCredentials(false) // Tắt credential nếu dùng allowedOrigins("*")
                .maxAge(3600); // Lưu cache thông tin CORS trong 1 giờ
    }
}
