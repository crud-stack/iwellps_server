package com.iwell.eye.infra.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmailOverlapResponse extends EyeBaseVO {
    private static final long serialVersionUID = -2997290483199789645L;
    @JsonProperty("isRegist")
    private boolean isRegist;
}
