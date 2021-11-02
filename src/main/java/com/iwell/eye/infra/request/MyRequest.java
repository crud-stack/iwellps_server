package com.iwell.eye.infra.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MyRequest extends EyeBaseVO {
    private static final long serialVersionUID = 7056631406318870372L;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    private int mktYn;
    private String newPassWord;
    private String passWord;
    private String newPassWordCheck;
    private String phone;
    private String spot;
}
