package com.iwell.eye.service;

import com.iwell.eye.common.base.EyeBaseService;
import com.iwell.eye.common.constant.CommonConstant;
import com.iwell.eye.common.exception.global.EyeApiException;
import com.iwell.eye.dao.MyDAO;
import com.iwell.eye.infra.IsCompleteResponse;
import com.iwell.eye.infra.StatusResponse;
import com.iwell.eye.infra.request.MyRequest;
import com.iwell.eye.infra.response.MyResponse;
import com.iwell.eye.model.CustVO;
import com.iwell.eye.model.UserCoCheckVO;
import com.iwell.eye.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MyService extends EyeBaseService {

    @Autowired
    CommonService commonService;

    @Autowired
    MyDAO myDAO;

    public StatusResponse<MyResponse> postMyInfo(MyRequest form){

        UserCoCheckVO userCoCheckVO = commonService.agentId();
        MyResponse myResponse = new MyResponse();

        if(form.isAdmin()){

            UserVO userVo = new UserVO();
            userVo.setUserSid(userCoCheckVO.getUserSid());
            userVo = myDAO.postAdminInfo(userVo);
            myResponse.setAdminInfo(
                    userVo.getUserNm()
                    ,userVo.getEmail()
                    ,userVo.getSex()
            );

        }else{

            CustVO custVO = new CustVO();
            custVO.setCustSid(userCoCheckVO.getUserSid());
            custVO = myDAO.postCustInfo(custVO);
            myResponse.setCustInfo(
                    custVO.getCustNm()
                    ,custVO.getPhone()
                    ,custVO.getSex()
                    ,custVO.getBirthDay()
                    ,custVO.getMktYn()
                    ,custVO.getMktDt()
            );

        }

        StatusResponse<MyResponse> result = new StatusResponse<MyResponse>();
        result.setData(myResponse);
        return result;
    }


    public StatusResponse<IsCompleteResponse> putSecede(){
        UserCoCheckVO userCoCheckVO = commonService.agentId();
        CustVO custVO = new CustVO();
        custVO.setCustSid(userCoCheckVO.getUserSid());

        custVO =  myDAO.putSecede(custVO);

        if(custVO.getCode() != CommonConstant.ERR_SUCCESS){
            throw new EyeApiException(custVO.getCode(),custVO.getMsg());
        }

        StatusResponse<IsCompleteResponse> result = new StatusResponse<IsCompleteResponse>();
        IsCompleteResponse isCompleteResponse = new IsCompleteResponse();
        isCompleteResponse.setSuccess(custVO.getCode()==CommonConstant.ERR_SUCCESS?true:false);
        result.setData(isCompleteResponse);
        return result;
    }

    public StatusResponse<MyResponse> putMyInfo(MyRequest form){
        UserCoCheckVO userCoCheckVO = commonService.agentId();


        CustVO custVO = new CustVO();
        custVO.setMktYn(form.getMktYn());
        custVO.setCustSid(userCoCheckVO.getUserSid());
        custVO = myDAO.putCustInfo(custVO);

        MyResponse myResponse = new MyResponse();
        myResponse.setCustInfo(
                custVO.getCustNm()
                ,custVO.getPhone()
                ,custVO.getSex()
                ,custVO.getBirthDay()
                ,custVO.getMktYn()
                ,custVO.getMktDt()
        );

        StatusResponse<MyResponse> result = new StatusResponse<MyResponse>();
        result.setData(myResponse);
        return result;
    }

}
