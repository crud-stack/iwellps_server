package com.iwell.eye.common.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class ApiUserVO implements Serializable{
	private static final long serialVersionUID = 8996177126837951223L;
	
//	private String UserSID;
//	private String password;
//	private String grantType;
//	private String role;
//	private String useYn;
//	private int accessNo;
	private String role;
	private String agentId;
	private String password;
//	private String usrSid;
//	private String mail;
//	private String role;
//	private String password;
//	private String clientId;
//	private int pwState;
//	private String clientSecret;
//	private String refreshToken;


}
