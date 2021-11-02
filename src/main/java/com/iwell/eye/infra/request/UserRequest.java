package com.iwell.eye.infra.request;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRequest extends EyeBaseVO {
    private static final long serialVersionUID = 1249450151199535148L;
    private String email;
    private String userNm;
    private String phone;
    private int sex;
    private String spot;
    private String auth;
}
