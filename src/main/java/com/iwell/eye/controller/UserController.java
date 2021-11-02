package com.iwell.eye.controller;

import com.iwell.eye.common.base.EyeBaseController;
import com.iwell.eye.common.constant.CommonConstant;
import com.iwell.eye.common.exception.global.EyeApiException;
import com.iwell.eye.infra.StatusResponse;
import com.iwell.eye.infra.request.EmailOverlapRequest;
import com.iwell.eye.infra.request.UserRequest;
import com.iwell.eye.infra.response.EmailOverlapResponse;
import com.iwell.eye.infra.response.UserResponse;
import com.iwell.eye.service.UserService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController extends EyeBaseController {

    @Autowired
    UserService userService;

    @PostMapping("/emailOverlap")
    public StatusResponse<EmailOverlapResponse> postEmailOverlap(@RequestBody EmailOverlapRequest form){
        if(StringUtils.isBlank(form.getEmail())){
            throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND,"이메일은 필수값 입니다.");
        }

        return userService.postEmailOverlap(form);
    }


    @PostMapping("/user")
    public StatusResponse<UserResponse> postUserRegist(@RequestBody UserRequest form){
        if(StringUtils.isBlank(form.getEmail())){
            throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND,"이메일은 필수값 입니다.");
        }
        if(StringUtils.isBlank(form.getPhone())){
            throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND,"휴대전화 번호는 필수값 입니다.");
        }
        if(StringUtils.isBlank(form.getAuth())){
            throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND,"권한은 필수값 입니다.");
        }
        if(StringUtils.isBlank(form.getUserNm())){
            throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND,"사용자명은 필수값 입니다.");
        }
        return null;
    }

}
