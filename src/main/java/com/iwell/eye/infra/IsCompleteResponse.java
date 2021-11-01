package com.iwell.eye.infra;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class IsCompleteResponse extends EyeBaseVO {
    private static final long serialVersionUID = -6557790008812336051L;
    @JsonProperty("isSuccess")
    private boolean isSuccess;
}
