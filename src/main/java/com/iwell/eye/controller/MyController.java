package com.iwell.eye.controller;

import com.iwell.eye.common.base.EyeBaseController;
import com.iwell.eye.infra.IsCompleteResponse;
import com.iwell.eye.infra.StatusResponse;
import com.iwell.eye.infra.request.LoginRequest;
import com.iwell.eye.infra.request.MyRequest;
import com.iwell.eye.infra.response.LoginResponse;
import com.iwell.eye.infra.response.MyResponse;
import com.iwell.eye.service.MyService;
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
        return myService.putMyInfo(form);
    }

}
