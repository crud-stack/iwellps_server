package com.iwell.eye.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


public class ApiWebConfigFactory implements WebMvcConfigurer {
	@Autowired
    AutowireCapableBeanFactory beanFactory;
	
	@Bean
	public RequestContextListener requestContextListener(){
	    return new RequestContextListener();
	} 
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//        		.allowedHeaders("Origin","Content-Type", "X-Auth-Token")
//        		.allowedMethods("GET", "POST","OPTIONS","PUT")
//                .allowedOrigins("*");
//	}

    @Bean
    public ErrorPageFilter errorPageFilter() {
          return new ErrorPageFilter();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean DisabledErrorPageFilter(ErrorPageFilter filter) {
          FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
          filterRegistration.setFilter(filter); 
          filterRegistration.setName("disabledErrorPageFilter"); 
          filterRegistration.setEnabled(false); 
          return filterRegistration; 
    }
    
}
