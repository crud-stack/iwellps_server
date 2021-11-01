package com.iwell.eye.common.util;

import com.iwell.eye.common.constant.CommonConstant;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CryptoUtil {
	
	final protected static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	/**
	 * AES 256으로 평문을 암호화
	 * 
	 * @param plainText: 평문
	 * @param key:       암호화 키
	 * @return
	 * @throws Exception
	 */
	public static String encryptAES256(String plainText, String key) throws Exception {
		byte[] ivBytes;
		byte[] bytes = new byte[20];
		byte[] saltBytes = bytes;

		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);

		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 65556, 256);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		// encrypting the word
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
		// prepend salt and vi
		byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
		System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
		System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
		System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);

		return new Base64().encodeToString(buffer);
	}

	/**
	 * AES 256으로 암호문을 복호화
	 * 
	 * @param encryptedText: 암호문
	 * @param key:           암호화 키
	 * @return
	 * @throws Exception
	 */
	public static String decryptAES256(String encryptedText, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		// strip off the salt and iv
		ByteBuffer buffer = ByteBuffer.wrap(new Base64().decode(encryptedText));
		byte[] saltBytes = new byte[20];
		byte[] ivBytes1 = new byte[cipher.getBlockSize()];
		byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes1.length];

		buffer.get(saltBytes, 0, saltBytes.length);
		buffer.get(ivBytes1, 0, ivBytes1.length);
		buffer.get(encryptedTextBytes);

		// Deriving the key
		byte[] decryptedTextBytes = null;
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 65556, 256);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes1));

		try {
			decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		} catch (Exception e) {
			log.error("Error decrypt : \r\nencryptedText on :" + encryptedText);
			log.error(e.getMessage());
			throw new Exception(e);
		}

		return new String(decryptedTextBytes);
	}
	
	public static String encryptAES256WithSalt(String plainText) throws Exception {
		return encryptAES256(plainText, CommonConstant.AES256KEY);
	}	
	
	public static String decryptAES256WithSalt(String encryptedText) throws Exception {
		if(encryptedText != null && encryptedText.getBytes().length > 0) {
			return decryptAES256(encryptedText, CommonConstant.AES256KEY);
		}else {
			return null;
		}
	}
	
	/**
	 * SHA 512 Message Digest
	 * @param msg
	 * @return
	 * @throws Exception 
	 * 한글이 포함된 평문도 MSSQL의 HASHBYTES 결과와 동일하게 출력하기 위해 'MS949' 캐릭터셋 설정
		EX)MSSQL
		SELECT CONVERT(varchar(max), HASHBYTES ('SHA2_512', 'SJKIM') ,2) 
		UNION ALL
		SELECT CONVERT(varchar(max), HASHBYTES ('SHA2_512', '김성준') ,2) 
		
		EX)JAVA		
		System.out.println(CryptoUtil.encryptSHA512("SJKIM"));
		System.out.println(CryptoUtil.encryptSHA512("김성준"));		
	 */
	public static String encryptSHA512(String msg)  {
	    MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
		    md.update(msg.getBytes("UTF-8"));
		    return byteToHexString(md.digest()).toUpperCase();	//MSSQL HASHBYTES가 대문자로 리턴하여 동일하게 변경
		} catch (Exception e) {
			log.error("CryptoUtil encryptSHA512 Error", e);
			return null;
		}

	}
	
	/**
	 * 해시된 데이터는 바이트 배열의 바이너리 데이터이므로 16진수 문자열로 변환
	 * @param data
	 * @return
	 */
	private static String byteToHexString(byte[] data) throws Exception {
	    StringBuilder sb = new StringBuilder();
	    for(byte b : data) {
	        sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	}
	
	public static String encryptMD5(String plainText) throws NoSuchAlgorithmException {
		return encryptMD5WithSalt(null, plainText);
	}

	public static String encryptMD5WithSalt(String salt, String plainText) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		if (salt != null) {
			md.update(salt.getBytes());
		}
		md.update(plainText.getBytes());

		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

}
