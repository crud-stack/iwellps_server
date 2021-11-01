package com.iwell.eye.common.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class SysLogVO implements Serializable{
	private static final long serialVersionUID = -2840780037869171591L;
	
	// for API
	private String arrow;
	private String url;
	private String method;
	private String hder;
	private String body;
	
	// for sql
	private String note;
	private String sqlQry;


	// for sql procedure
	private String errorMessage = "";
	private int errorNumber;
	private int errorState;
	private int errorSeverity;
	private String serverName;
	private String procName;
	private long lineNumber;


	private String regSid;
}
