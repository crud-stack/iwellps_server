package com.iwell.eye.model;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginVO extends EyeBaseVO {
    private static final long serialVersionUID = -5125976035613775308L;
    private String userSid;
    private String userNm;
    private String email;
    private String pw;
    private String birthDay;
    private String phone;
    private String auth;

    public void setAdminLogin(String email, String pw){
        this.email = email;
        this.pw = pw;
    }

    public void setUserLogin(String userNm, String birthDay, String phone){
        this.userNm = userNm;
        this.birthDay = birthDay;
        this.phone = phone;
    }

}
