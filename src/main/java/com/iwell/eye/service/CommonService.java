package com.iwell.eye.service;

import com.iwell.eye.common.base.EyeBaseService;
import com.iwell.eye.model.UserCoCheckVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

@Service
public class CommonService extends EyeBaseService {

    //token 정보 추출
    public UserCoCheckVO agentId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserCoCheckVO mberCoVo = new UserCoCheckVO();
        mberCoVo.setData(((OAuth2Authentication) authentication).getOAuth2Request().getClientId(),authentication.getName());
        return mberCoVo;
    }

}
