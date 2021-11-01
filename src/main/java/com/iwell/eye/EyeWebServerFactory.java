package com.iwell.eye;


import com.iwell.eye.common.config.ApiServletWebServerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;


@Component
public class EyeWebServerFactory implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
	@Value("${server.port.eye}")
	protected int port;
	@Value("${server.tomcat.connection-timeout}")
	protected int timeout;

	// UnderTow
//	@Override
//	public void customize(UndertowServletWebServerFactory factory) {
//		ApiServletWebServerFactory.customize(factory, port, timeout);
//	}

	// Tomcat
	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		ApiServletWebServerFactory.customize(factory, port, timeout);
	}
}

