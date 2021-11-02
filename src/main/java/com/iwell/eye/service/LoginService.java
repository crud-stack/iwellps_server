package com.iwell.eye.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwell.eye.common.base.EyeBaseService;
import com.iwell.eye.common.constant.CommonConstant;
import com.iwell.eye.common.exception.RestException;
import com.iwell.eye.common.exception.global.EyeApiException;
import com.iwell.eye.common.exception.global.EyeException;
import com.iwell.eye.common.util.CryptoUtil;
import com.iwell.eye.dao.LoginDAO;
import com.iwell.eye.infra.StatusResponse;
import com.iwell.eye.infra.request.LoginRequest;
import com.iwell.eye.infra.response.LoginResponse;
import com.iwell.eye.infra.response.TokenResponse;
import com.iwell.eye.model.LoginVO;
import com.iwell.eye.model.UserVO;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class LoginService extends EyeBaseService {

    @Autowired
    LoginDAO loginDAO;

    public StatusResponse<LoginResponse> postLogin(LoginRequest form) throws Exception{
        LoginVO loginVO = new LoginVO();

        if(!StringUtils.isBlank(form.getUserNm())){
            loginVO.setUserLogin(
                        form.getUserNm()
                    ,   form.getBirthDay()
                    ,   form.getPhone()
            );

            loginVO = loginDAO.postCustLogin(loginVO);

        }

        if(!StringUtils.isBlank(form.getEmail())){
            loginVO.setAdminLogin(
                        form.getEmail()
                    ,   CryptoUtil.encryptSHA512(form.getPw())
            );

            loginVO = loginDAO.postUserLogin(loginVO);

        }

        if(loginVO == null){
            throw new EyeApiException(CommonConstant.ERR_INVALID, "사용자 정보가 없습니다.");
        }

        String url = API_ROOT_MESSAGE+CommonConstant.AUTH_ACCESS_TOKEN;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("grant_type", "password");
        builder.queryParam("username",loginVO.getUserSid());
        builder.queryParam("password",StringUtils.isBlank(form.getPw())?form.getPhone():form.getPw());
        System.out.println(CryptoUtil.encryptSHA512("Alsrn1028!"));


        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(CommonConstant.AUTH_CLIENT_ID, CommonConstant.AUTH_CLIENT_PW);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> sejongResponse = null;
        TokenResponse tokenRes = new TokenResponse();

        try{

            sejongResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, new HttpEntity<String>(headers), String.class);
            String jsonStr = sejongResponse.getBody();
            if (logger.isDebugEnabled()) {
                logger.debug("=====================================");
                logger.debug("수신바디 확인 \r\n{}", jsonStr);
                logger.debug("=====================================");
            }

            tokenRes = new ObjectMapper().readValue(jsonStr, new TypeReference<TokenResponse>() {});

        }catch (RestException re) {
            throw new EyeApiException(re.getCode(), re.getMessage());
        }catch (HttpClientErrorException he){
            throw new EyeApiException(he.getRawStatusCode(),"[CRUDSYSTEM] Require Certification::" + he.getMessage());
        } catch (Exception e) {
            throw new EyeApiException(CommonConstant.ERR_CHANNEL,"관리자에게 문의해 주세요.");
        }

        StatusResponse<LoginResponse> result = new StatusResponse<>();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccess_token(tokenRes.getAccess_token());
        loginResponse.setExpires_in(tokenRes.getExpires_in());
        loginResponse.setToken_type(tokenRes.getToken_type());
        loginResponse.setRole(tokenRes.getRole());
        result.setData(loginResponse);
        return result;
    }

}
