package com.ssafy.sns;

import com.ssafy.sns.jwt.JwtInterceptor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class SnsApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SnsApplication.class, args);
	}

	@Autowired
	private final JwtInterceptor jwtInterceptor;

	// JWTInterceptor를 설치한다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/**") // 기본 적용 경로
				.excludePathPatterns(
						Arrays.asList(
								"/v2/api-docs",
								"/swagger-resources/**",
								"/swagger-ui.html",
								"/webjars/**",
								"/users/login",
								"/users/join",
								"/users/check",
								"/swagger-ui",
								"/swagger-ui.html/**")
						);// 적용 제외 경로

	}

	// Interceptor를 이용해서 처리하므로 전역의 Cors Origin 처리를 해준다.
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*")
				.exposedHeaders("Authorization");
	}
}
