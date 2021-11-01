package com.iwell.eye.common.handler;

import com.iwell.eye.common.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/*
 * @author KMG
 * Oauth2.0 자격증명 unsername, password 사용자정보와 틀릴경우 exception 호출
 */
@Component
public class AuthorizationOauthEndPoint extends DefaultWebResponseExceptionTranslator {

    final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        logger.warn("● Authorization OauthEndPoint Detection ●");
        Map responseMap = new HashMap();
        responseMap.put("statusCode", 401);
        responseMap.put("statusMessage", "[CRUDSYSTEM] Bad credentials::" + e.getMessage());
        responseMap.put("responseTime", DateUtil.getCurrentTime());
        return new ResponseEntity(responseMap, HttpStatus.UNAUTHORIZED);
    }
}
