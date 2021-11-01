package com.iwell.eye.infra.request;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginRequest extends EyeBaseVO {
    private static final long serialVersionUID = -5435402839374090382L;
    private String email; //이메일
    private String pw;  //비밀번호
    private String userNm; //사용자명
    private String phone; //휴대전화
    private String birthDay;
}
