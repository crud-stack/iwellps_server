package com.iwell.eye.common.config;

import com.iwell.eye.common.service.ApiUserDetailsService;
import com.iwell.eye.common.util.CustomPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ApiSecurityConfigFactory extends WebSecurityConfigurerAdapter {

	public ApiSecurityConfigFactory() {
		System.out.println("┌──────────────────────────────");
		System.out.println("│ Doing ApiSecurityConfigFactory ...");
		System.out.println("└──────────────────────────────");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.httpBasic().disable()
		.csrf().disable()
		.cors().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}

/*	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		*//*return super.authenticationManager();*//*
		return super.authenticationManager();
	}*/
	@Bean
	protected AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new ApiUserDetailsService();
	}


}