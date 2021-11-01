package com.iwell.eye.common.model;

import com.iwell.eye.common.base.CommonBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserJoinInfoVO extends CommonBaseVO {

    private static final long serialVersionUID = 5920487874268165062L;

    private String mail;
    private String clientId;
    private String ip;
    private String browser;
    private String browserVer;
    private String os;
    private String osVer;
    private String device;


}
