package com.iwell.eye.common.config;


import com.iwell.eye.common.config.ApiResourceServerConfigFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ApiResourceServerConfigFactory {


}
