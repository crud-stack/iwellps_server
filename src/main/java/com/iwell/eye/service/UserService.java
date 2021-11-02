package com.iwell.eye.service;

import com.iwell.eye.common.base.EyeBaseService;
import com.iwell.eye.common.constant.CommonConstant;
import com.iwell.eye.common.exception.global.EyeApiException;
import com.iwell.eye.common.util.CryptoUtil;
import com.iwell.eye.dao.UserDAO;
import com.iwell.eye.infra.StatusResponse;
import com.iwell.eye.infra.request.EmailOverlapRequest;
import com.iwell.eye.infra.request.UserRequest;
import com.iwell.eye.infra.response.EmailOverlapResponse;
import com.iwell.eye.infra.response.UserResponse;
import com.iwell.eye.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService extends EyeBaseService {

    @Autowired
    UserDAO userDAO;

    public StatusResponse<EmailOverlapResponse> postEmailOverlap(EmailOverlapRequest form){

        UserVO userVO = new UserVO();
        userVO.setEmail(form.getEmail());

        userVO = userDAO.postEmailOverlap(userVO);

        if(userVO.getCode() != CommonConstant.ERR_SUCCESS){
            throw new EyeApiException(userVO.getCode(), userVO.getMsg());
        }

        StatusResponse<EmailOverlapResponse> result = new StatusResponse<EmailOverlapResponse>();
        EmailOverlapResponse emailOverlapResponse = new EmailOverlapResponse();
        emailOverlapResponse.setRegist(userVO.getCode() == CommonConstant.ERR_SUCCESS ? true : false);
        result.setData(emailOverlapResponse);
        return result;
    }


    public StatusResponse<UserResponse> postUserRegist(UserRequest form){

        UserVO userVO = new UserVO();
        userVO.setRegist(
                    form.getUserNm()
                ,   form.getSex()
                ,   form.getEmail()
                ,   CryptoUtil.encryptSHA512(form.getEmail())
                ,   form.getAuth()
                ,   form.getPhone()
                ,   form.getSpot()
        );





        return null;
    }
}
