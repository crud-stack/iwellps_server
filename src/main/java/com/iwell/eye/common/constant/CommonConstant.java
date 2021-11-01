package com.iwell.eye.common.constant;

public class CommonConstant {
	public static final String AES256KEY = "crudsystem1008!";
	public static final String AUTH_ACCESS_TOKEN = "/auth/authorize";
	public static final String AUTH_CLIENT_ID = "iwellps";
	public static final String AUTH_CLIENT_PW = "123";
	// --------------------------------------------------------------
	// ------------------------------------ throw new ApiException(CommonConstant.);--------------------------
	// 공통
	public static final int ERR_SUCCESS = 200; // 성공
	public static final int ERR_INTERNAL = 500; //내부 서버 오류
	public static final int	ERR_CHANNEL = 4005; //채널 응답 오류
	public static final int ERR_TIMEOUT = 408; //Time Out
	public static final int ERR_INVALID = 401; //접근 권한이 없습니다.
	public static final int ERR_NOT_ALLOW_IP = 4002; //허용아이피가 아닙니다.
	public static final int	ERR_DATA_NOT_FOUND	= 4000; //Json 필드 필수값 누락
	public static final int ERR_JSON_ATTR_IS_INVALID = 4001; //JSON항목이 올바르지 않음
	public static final int	ERR_DATA_MATCH = 4003; //동일한 데이터가 존재 합니다.
	public static final int	ERR_DATA_NOT_MATCH = 4004; //일치하는 데이터가 없습니다.
	public static final int	ERR_DATA_NOT_SUPPORT = 4005; //지원하지 않습니다.
	public static final int	ERR_DATA_INTERNAL = 5000; //관리자에게 문의해 주세요.
	public static final int	ERR_DATA_INTERNAL_CODE = 5001; //관리자에게 문의해 주세요.






}
