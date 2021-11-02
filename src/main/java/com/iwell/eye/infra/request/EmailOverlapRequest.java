package com.iwell.eye.infra.request;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmailOverlapRequest extends EyeBaseVO {
    private static final long serialVersionUID = 1778383284837736437L;
    private String email;
}
