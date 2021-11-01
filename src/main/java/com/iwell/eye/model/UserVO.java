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
    private String auth;
    private int sex;

}
