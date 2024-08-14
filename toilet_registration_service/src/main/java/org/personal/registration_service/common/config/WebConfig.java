package org.personal.registration_service.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:5173") // Vue.js 개발 서버 도메인
			.allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드
			.allowedHeaders("*") // 허용할 헤더
			.allowCredentials(true) // 자격 증명 허용 (쿠키 등)
			.maxAge(3600); // 프리플라이트 요청 캐시 시간 (초)
	}
}
