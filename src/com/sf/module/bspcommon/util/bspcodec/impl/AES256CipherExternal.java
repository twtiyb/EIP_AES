package com.sf.module.bspcommon.util.bspcodec.impl;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 与外部客户交互时，使用的AES256加密方式 20150624
 * 
 * 
 * 
 */
public class AES256CipherExternal {
	private static Logger log = LoggerFactory.getLogger(AES256CipherExternal.class);
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00 };

	private Cipher cipherEncrypt = null;
	private Cipher cipherDncrypt = null;

	public AES256CipherExternal(String key) {
		try {
			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			cipherEncrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipherEncrypt.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

			cipherDncrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipherDncrypt.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
		} catch (Exception e) {
			log.error("AES256CipherExternal init Fail,key:" + key, e);
		}
	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String AES_Encode(String str) throws Exception {
		byte[] textBytes = str.getBytes("UTF-8");

		return Base64.encodeBase64String(cipherEncrypt.doFinal(textBytes));
	}

	/**
	 * 解密
	 * 
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String AES_Decode(String str) throws Exception {

		byte[] textBytes = Base64.decodeBase64(str);
		// byte[] textBytes = str.getBytes("UTF-8");
		return new String(cipherDncrypt.doFinal(textBytes), "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		String key = "wUXqQMiFv+o0VNn0VHofApu5MlgHHPSi";
		AES256CipherExternal aes256 = new AES256CipherExternal(key);

		String plainText;
		String encodeText;
		String decodeText;
		// Encrypt
		plainText = "test中文信息内容1234567890";
		encodeText = aes256.AES_Encode(plainText);
		System.out.println("AES256_Encode : " + encodeText);

		// Decrypt
		decodeText = aes256.AES_Decode(encodeText);
		System.out.println("AES256_Decode : " + decodeText);

	}
}