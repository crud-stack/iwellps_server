package com.iwell.eye.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.invoke.MethodHandles;

public class CustomPasswordEncoder implements PasswordEncoder {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Override
	public String encode(CharSequence rawPassword) {
		try {
			return CryptoUtil.encryptSHA512(rawPassword.toString());
		} catch (Exception e) {
			logger.error("● apiomPasswordEncoder::encode::Error! ●", e);
			throw new RuntimeException("apiomPasswordEncoder encode Error!");
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		try {
			if(CryptoUtil.encryptSHA512(rawPassword.toString()).equals(encodedPassword)) {
				return true;
			}
		} catch (Exception e) {
			logger.error("● apiomPasswordEncoder::matches::Error! ●", e);
			throw new RuntimeException("apiomPasswordEncoder matches Error!");
		}
		return false;
	}

}
