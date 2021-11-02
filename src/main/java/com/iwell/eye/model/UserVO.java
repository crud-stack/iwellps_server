package com.iwell.eye.model;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVO extends EyeBaseVO {
    private static final long serialVersionUID = -5125976035613775308L;
    private String userSid;
    private String userNm;
    private String email;
    private String pw;
    private String newPw;
    private String auth;
    private int sex;
    private String phone;
    private String spot;

    private int code;
    private String msg;

    public void setRegist(String userNm, int sex, String email, String pw, String auth, String phone, String spot ){
        this.userNm = userNm;
        this.email = email;
        this.pw = pw;
        this.sex = sex;
        this.auth = auth;
        this.phone = phone;
        this.spot = spot;
    }

}
