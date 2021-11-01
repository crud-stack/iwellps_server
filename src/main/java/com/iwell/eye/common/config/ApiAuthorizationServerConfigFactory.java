package com.iwell.eye.common.config;

import com.iwell.eye.common.handler.AuthorizationOauthEndPoint;
import com.iwell.eye.common.tokener.CustomTokenEnhancer;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class ApiAuthorizationServerConfigFactory extends AuthorizationServerConfigurerAdapter{

	public ApiAuthorizationServerConfigFactory() {
		System.out.println("┌──────────────────────────────");
		System.out.println("│ Doing ApiAuthorizationServerConfigFactory ...");
		System.out.println("└──────────────────────────────");
	}

	@Autowired
	private DataSource dataSource;
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}
	
	 @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        CorsFilter filter = new CorsFilter(corsConfigurationSource());
        security.addTokenEndpointAuthenticationFilter(filter);
        security.checkTokenAccess("permitAll()");
    }
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//Jwt 항목 추가 client_id, authorities은 기본 추가이나 별도의 추가가 없으면 tokenEnhancer() 삭제할것.
		 TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		    tokenEnhancerChain.setTokenEnhancers(
		      Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter));
		endpoints
				.tokenStore(tokenStore)
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
				.pathMapping("/oauth/token", "/auth/authorize")
				.pathMapping("/oauth/check_token","/auth/check_token")
				.tokenEnhancer(tokenEnhancerChain) //default jwt + custom jwt add
				.exceptionTranslator(authorizationWebResponseExceptionTranslator())
				.reuseRefreshTokens(false); //refresh_token Expiration time
				//.accessTokenConverter(jwtAccessTokenConverter); //custom jwt 추가로인해 선언위치 변경으로 토큰암호화 주석  	
	}

	@Bean
	public WebResponseExceptionTranslator authorizationWebResponseExceptionTranslator() {
		return new AuthorizationOauthEndPoint();
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	public ClientDetailsService clientDetailsService(DataSource dataSource) {
		return new JdbcClientDetailsService(dataSource);
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("/key/crud-oauth2jwt.jks"), "crudsystem1008!".toCharArray());
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("crud-oauth2jwt"));
		converter.setVerifierKey(getPublicKey());
		return converter;
	}
	
	
	@Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
	}
	

	private String getPublicKey() {
		Resource resource = new ClassPathResource("/key/crud-oauth2jwt.public");
		try {
			return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
