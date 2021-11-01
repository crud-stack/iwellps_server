package com.iwell.eye.controller;

import com.iwell.eye.common.base.EyeBaseController;
import com.iwell.eye.common.constant.CommonConstant;
import com.iwell.eye.common.exception.global.EyeApiException;
import com.iwell.eye.infra.StatusResponse;
import com.iwell.eye.infra.request.LoginRequest;
import com.iwell.eye.infra.response.LoginResponse;
import com.iwell.eye.service.LoginService;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RestController
@RequestMapping("/")
public class LoginController extends EyeBaseController {

    @Autowired
    LoginService loginService;

    /**
     * SSMSG MESSAGE MODULE RUNNING PAGE
     *
     * @author KMG
     * @param form
     * @return DenyPhoneClientResponse
     */
    @PostMapping("/login")
    public StatusResponse<LoginResponse> postLogin(@RequestBody LoginRequest form) throws Exception {

        if(StringUtils.isBlank(form.getEmail()) && StringUtils.isBlank(form.getUserNm())){
            throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND, "이메일 또는 사용자명은 필수 입니다.");
        }

        if(!StringUtils.isBlank(form.getEmail()) || !StringUtils.isBlank(form.getPw())){
            if(StringUtils.isBlank(form.getPw())){
                throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND, "비밀번호는 필수 입니다.");
            }
            if(StringUtils.isBlank(form.getEmail())){
                throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND, "비밀번호는 필수 입니다.");
            }

        }

        if(!StringUtils.isBlank(form.getUserNm()) || !StringUtils.isBlank(form.getPhone()) || !StringUtils.isBlank(form.getBirthDay())){
            if(StringUtils.isBlank(form.getUserNm())){
                throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND, "사용자명은 필수 입니다.");
            }
            if(StringUtils.isBlank(form.getPhone())){
                throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND, "휴대전화는 필수 입니다.");
            }
            if(StringUtils.isBlank(form.getBirthDay())){
                throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND, "생년월일은 필수 입니다.");
            }
        }

        return loginService.postLogin(form);

    }

}
