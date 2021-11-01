package com.iwell.eye.common.config;

import com.iwell.eye.common.config.ApiAuthorizationServerConfigFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends ApiAuthorizationServerConfigFactory {


}