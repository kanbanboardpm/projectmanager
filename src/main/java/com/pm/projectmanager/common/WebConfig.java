package com.pm.projectmanager.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    private static final String[] ALLOWED_ORIGINS = {
            "http://localhost:8888",
            "http://localhost:5173",
            "http://43.201.146.41:8888" // EC2의 IP 추가
    };

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// 모든 경로에 대해 CORS 허용
				registry.addMapping("/**")
					.allowedOrigins(ALLOWED_ORIGINS.clone())
					.allowedMethods("*")
					.allowedHeaders("*")
					.exposedHeaders("Authorization") // 응답 헤더 중 Authorization 헤더를 노출
					.allowCredentials(true);
			}
		};
	}
}