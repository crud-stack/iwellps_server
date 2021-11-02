package com.iwell.eye.infra.response;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MyResponse extends EyeBaseVO {
    private static final long serialVersionUID = 2466280653267957785L;
    private String userNm;
    private String custNm;
    private String phone;
    private String spot;
    private String email;
    private int sex;
    private String birthDay;
    private int mktYn;
    private String mktDt;

    public void setAdminInfo(String userNm, String email, int sex, String phone, String spot){
        this.userNm = userNm;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.spot = spot;
    }

    public void setCustInfo(String custNm, String phone, int sex, String birthDay, int mktYn, String mktDt){
        this.custNm = custNm;
        this.phone = phone;
        this.sex = sex;
        this.birthDay = birthDay;
        this.mktYn = mktYn;
        this.mktDt = mktDt;
    }

}
