package com.iwell.eye.infra.response;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TokenResponse extends EyeBaseVO {
    private static final long serialVersionUID = -3477207943688812522L;
    private String access_token;
    private String refresh_token;
    private String token_type;
    private long expires_in;
    private String scope;
    private String role;
    private String jti;

    private int statusCode;
    private String statusMessage;

    public TokenResponse(){
        this.statusCode = 200;
        this.statusMessage = "success";
    }
}
