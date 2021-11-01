package com.iwell.eye.common.config;

import com.iwell.eye.common.handler.ApiAccessDeniedHandler;
import com.iwell.eye.common.handler.ApiAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

public class ApiResourceServerConfigFactory extends ResourceServerConfigurerAdapter{
	@Value("${security.token.require}")
	private boolean tokenCheck;
	
	public ApiResourceServerConfigFactory() {
		System.out.println("┌──────────────────────────────");
		System.out.println("│ Doing ApiResourceServerConfigFactory ...");
		System.out.println("└──────────────────────────────");
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.accessDeniedHandler(new ApiAccessDeniedHandler());
		resources.authenticationEntryPoint(new ApiAuthenticationEntryPoint());
	}
	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		if(tokenCheck) {
////			http.authorizeRequests()
////					.antMatchers("/event/**").permitAll()
////					.antMatchers("/check/**").permitAll()
////					.antMatchers("/api/v1/**").authenticated();
////					.anyRequest().authenticated()
////					.and()
////					.requestMatchers()
////					.antMatchers("api/v1/**");
//			//내부통신용 API
//			http.authorizeRequests()
//					.antMatchers("/cust/**").permitAll()
//					.antMatchers("/auth/authoize").permitAll()
//					.antMatchers("/api/v1/**").authenticated()
//					.anyRequest().authenticated();
//		}else {
//			http.authorizeRequests().anyRequest().permitAll();
//		}
//		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		if(tokenCheck) {
			//내부통신용 API
			http.authorizeRequests().antMatchers("/login").permitAll();
			http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
			http.authorizeRequests().anyRequest().authenticated();
		}else {
			http.authorizeRequests().anyRequest().permitAll();
		}
		http.httpBasic().disable()
				.csrf().disable()
				.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}



}
