package com.iwell.eye.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class ConvertUtil {

	public static String objectToJsonString(Object src) {
		return objectToJsonString(src, null);
	}

	public static String objectToJsonString(Object src, SimpleFilterProvider filterProvider) {
		try {
			if ( null != filterProvider) {
				return new ObjectMapper().setFilterProvider(filterProvider).writeValueAsString(src);
			}
			else {
				return new ObjectMapper().writeValueAsString(src);
			}
		} catch (Exception e) {
			return null;
		}

	}
	public static MultiValueMap<String, String> objectToMultiValueMapString(Object src) {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			Map<String, String> map = new ObjectMapper().convertValue(src, new TypeReference<Map<String, String>>() {});

			params.setAll(map); // (4)
			return params;
		} catch (Exception e) {
			return null;
		}
	}


	public static MultiValueMap<String, Object> objectToMultiValueMap(Object src) {
		try {
			MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
			Map<String, Object> map = new ObjectMapper().convertValue(src, new TypeReference<Map<String, Object>>() {});

			params.setAll(map); // (4)
			return params;
		} catch (Exception e) {
			return null;
		}
	}


	public static long stringToLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return 0L;
		}
	}

	public static int stringToInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double stringToDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String nullToEmpty(String value) {
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}

	// "null" 문자열 에러처리
	public static boolean isBlank(String value) {
		if (StringUtils.isBlank(value) == false) {
			if ("null".equals(value)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	// 전각문자 -> 반각문자
	public static String toHalfChar(String src) {
		int rspace = 0; // space의 갯수
		StringBuffer strBuf = new StringBuffer();
		char c = 0;

		try {
			int nSrcLength = src.length();
			for (int i = 0; i < nSrcLength; i++) {
				c = src.charAt(i);
				// 영문이거나 특수 문자 일경우.
				if (c >= '！' && c <= '～') {
					c -= 0xfee0;
				} else if (c == '　') {
					c = 0x20;
				}

				if (c == ' ') {
					rspace++;
				} else {
					rspace = 0;
				}

				// 문자열 버퍼에 변환된 문자를 쌓는다
				strBuf.append(c);
			}

			if (rspace > 0) {
				strBuf.delete(strBuf.length() - rspace, strBuf.length());
			}
		} catch (Exception e) {
			return src;
		}
		return strBuf.toString();
	}

}
