package com.iwell.eye.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class OtherConfig {
	public OtherConfig() {
		System.out.println("┌──────────────────────────────");
		System.out.println("│ Doing OtherConfig ...");
		System.out.println("└──────────────────────────────");
	}
	
	/*
	 * @Value(“${property.name}”) 
	 * 형태의 프로퍼티 어노테이션의 사용이 가능하게 해주는 설정이다
	 */
    @Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
