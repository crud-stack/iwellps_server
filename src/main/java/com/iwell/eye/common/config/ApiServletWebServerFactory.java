package com.iwell.eye.common.config;

import org.apache.coyote.http2.Http2Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

import java.nio.charset.StandardCharsets;

public class ApiServletWebServerFactory {

	// for Undertow
//	public static void apiomize(UndertowServletWebServerFactory factory, int port, int timeout) {
//		/* 
//		 * 부하테스트로 관찰해본 바 IO스레드와 Worker스레드 별도 지정없이 기본값 사용이 적절하다고 판단 
//		 * 논리스레드인 하이퍼스레드까지 가용하려 값 증가시켜 시도해보았으나 반응하지 않음
//		 * IoThreads Default 값은 서버의 하드웨어의 따라 가변 처리됨 -> Math.max(Runtime.getRuntime().availableProcessors(), 2)
//		 * WorkerThreads Default 값은 IoThreads * 8 로 가변 처리
//		 * */
//		factory.setContextPath("");
//        factory.setPort(port);    
//
//        factory.addDeploymentInfoapiomizers(deploymentInfo -> {
//            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
//            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
//            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
//        });
//	}

	// for Tomcat
	public static void customize(TomcatServletWebServerFactory factory, int port, int timeout) {
		// accept-count: 100
		// max-connections: -1
		// min-spare-threads: 50
		// max-threads: -1
		// max-swallow-size: 2MB
		// max-http-post-size: 2MB
		// connection-timeout: 10000
		// cluster.port: 5000

		factory.setContextPath("");
		factory.setPort(port);
		factory.setUriEncoding(StandardCharsets.UTF_8);
		factory.addConnectorCustomizers(connector -> {
			Http2Protocol http2Protocol = new Http2Protocol();
			http2Protocol.setMaxConcurrentStreamExecution(200);
			connector.addUpgradeProtocol(http2Protocol);
		});
	}
}
