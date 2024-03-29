package com.ssafy.materip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// SPRING Security 설정시에 필요함!
@Configuration
public class CORSConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
		.addMapping("/**") // REST API 경로
		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // HTTP Method
		.allowedOrigins("http://localhost:5173") // Client 측 주소
		.allowCredentials(true)
		.exposedHeaders("*")
		.maxAge(3600); // Pre-flight Caching
	}
}