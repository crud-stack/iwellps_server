package com.iwell.eye.infra.response;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResponse extends EyeBaseVO {
    private static final long serialVersionUID = -3700251322598345585L;
    private String access_token;
    private String token_type;
    private String role;
    private long expires_in;
}
