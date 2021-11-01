package com.iwell.eye.model;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustVO extends EyeBaseVO {
    private static final long serialVersionUID = 1286701246651125630L;
    private String custSid;
    private String custNm;
    private int sex;
    private String phone;
    private String birthDay;
    private int mktYn;
    private String mktDt;

    private int code;
    private String msg;


}
