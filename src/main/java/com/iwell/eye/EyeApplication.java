package com.iwell.eye;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@EnableCaching
public class EyeApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(EyeApplication.class, args);
		printScannedBeans(applicationContext);
		//printTomcatInfo(applicationContext);
		printComplete();
	}
	
	@Override 
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(EyeApplication.class);
	}
	
	private static void printScannedBeans(ApplicationContext applicationContext) {
		System.out.println ( "┌──────────────────────────────"	);
		System.out.println ( "│ Application run ...");
		System.out.println ( "│ ");
		for (String name : applicationContext.getBeanDefinitionNames()) {
			System.out.println ( "│ Scanned bean :: " + name);
		}
		System.out.println ( "└──────────────────────────────"	);
	}
	
	private static void printComplete() {
		System.out.println ( "┌──────────────────────────────");
		System.out.println ( "│ ");		
		System.out.println ( "│ Settings Complete");
		System.out.println ( "│ Enjoy Your Development");
		System.out.println ( "│ ");				
		System.out.println ( "└──────────────────────────────");
	}
	
	@SuppressWarnings("unused")
	private static void printTomcatInfo(ApplicationContext applicationContext) {
		TomcatServletWebServerFactory tomcatServletContainerFactory = applicationContext.getBean(TomcatServletWebServerFactory.class);
		ServerProperties serverProperties = applicationContext.getBean(ServerProperties.class);
		System.out.println("[ServerProperties]");
		System.out.println(ReflectionToStringBuilder.toString(serverProperties));
		System.out.println("[serverProperties.getTomcat()]");
		System.out.println(ReflectionToStringBuilder.toString(serverProperties.getTomcat()));
		System.out.println("[tomcatServletContainerFactory]");
		System.out.println(ReflectionToStringBuilder.toString(tomcatServletContainerFactory));
	}
	
}
