package com.iwell.eye.common.config;

import com.iwell.eye.common.exception.global.EyeRestControllerAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
public class WebConfig extends ApiWebConfigFactory {

	public WebConfig() {
		System.out.println ( "┌──────────────────────────────"	);
		System.out.println ( "│ Doing WebConfig ...");
		System.out.println ( "└──────────────────────────────"	);
	}
	
	/*
	 * Exception 처리
	 * RestController의 모든 throw Exception을 관장한다.
	 * Controller 진입 전 발생하는 Exception에 대한 처리는 불가능하다. (서블릿, 필터 등)
	 */
	@Bean
	public EyeRestControllerAdvice apiomRestControllerAdvice() {
		return new EyeRestControllerAdvice();
	}

}
