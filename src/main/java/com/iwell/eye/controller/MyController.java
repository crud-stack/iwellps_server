package com.iwell.eye.controller;

import com.iwell.eye.common.base.EyeBaseController;
import com.iwell.eye.common.constant.CommonConstant;
import com.iwell.eye.common.exception.global.EyeApiException;
import com.iwell.eye.infra.IsCompleteResponse;
import com.iwell.eye.infra.StatusResponse;
import com.iwell.eye.infra.request.LoginRequest;
import com.iwell.eye.infra.request.MyRequest;
import com.iwell.eye.infra.response.LoginResponse;
import com.iwell.eye.infra.response.MyResponse;
import com.iwell.eye.service.MyService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MyController extends EyeBaseController {

    @Autowired
    MyService myService;

    @PostMapping("/my")
    public StatusResponse<MyResponse> postMyInfo(@RequestBody MyRequest form) throws Exception {
        return myService.postMyInfo(form);
    }

    @PutMapping("/secede")
    public StatusResponse<IsCompleteResponse> putSecede(){
        return myService.putSecede();
    }

    @PutMapping("/my")
    public StatusResponse<MyResponse> putMyInfo(@RequestBody MyRequest form){

        if(form.isAdmin()){
            if(StringUtils.isBlank(form.getPassWord())){
                throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND, "비밀번호는 필수 입니다.");
            }

            if(!StringUtils.isBlank(form.getNewPassWord())){
                if(StringUtils.isBlank(form.getNewPassWordCheck())){
                    throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND, "패스워드 확인 값은 필수 입니다.");
                }
                if(!form.getNewPassWord().equals(form.getNewPassWordCheck())){
                    throw new EyeApiException(CommonConstant.ERR_DATA_NOT_FOUND, "비밀번호와 비밀번호 확인 값이 다릅니다.");
                }
            }
        }

        return myService.putMyInfo(form);
    }

}
