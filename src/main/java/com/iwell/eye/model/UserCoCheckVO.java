package com.iwell.eye.model;

import com.iwell.eye.common.base.EyeBaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserCoCheckVO extends EyeBaseVO {
    private static final long serialVersionUID = -626793472446520855L;
    private String agentId;
    private String userSid;

    public void setData(String agentId, String userSid){
        this.agentId = agentId;
        this.userSid =userSid;
    }
}
