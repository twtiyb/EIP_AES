package com.sf.module.bspcommon.util.bspcodec.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.axis.utils.StringUtils;

/**
 * 按加密密钥进行缓存AESCCBCCoder对象，提高频繁使用时的性能
 * 
 * @author 056235
 *
 */
public class AESCBCCoderFactory {
	static Map<String, AESCBCCoder> aesCbcCoderMap = new HashMap<String, AESCBCCoder>();

	public static AESCBCCoder getAESCBCCoderObject(int keySize, String keySeeds, String algorithm) {
		if (null == aesCbcCoderMap.get(keySeeds)) {
			if (StringUtils.isEmpty(algorithm)) {
				algorithm = "AES/CBC/PKCS5Padding";
			}

			AESCBCCoder aESCBCCoder = new AESCBCCoder(algorithm);

			aESCBCCoder.setKeySize(keySize);
			aESCBCCoder.setKeySeeds(keySeeds);

			aesCbcCoderMap.put(keySeeds, aESCBCCoder);
		}

		return aesCbcCoderMap.get(keySeeds);
	}
	
	public static String getAESCBCencodedata(String keySeeds, String data) {
		try {
		String algorithm=null;
		if (null == aesCbcCoderMap.get(keySeeds)) {
			if (StringUtils.isEmpty(algorithm)) {
				algorithm = "AES/CBC/PKCS5Padding";
			}

			AESCBCCoder aESCBCCoder = new AESCBCCoder(algorithm);

			aESCBCCoder.setKeySize(256);
			aESCBCCoder.setKeySeeds(keySeeds);

			aesCbcCoderMap.put(keySeeds, aESCBCCoder);
		}

		return aesCbcCoderMap.get(keySeeds).encryptToStringFromString(data);
		}catch(Exception e)
		{
			return "error";
		}
	}
	
	public static String getAESCBCdecodedata(String keySeeds, String data) {
		String algorithm=null;
		if (null == aesCbcCoderMap.get(keySeeds)) {
			if (StringUtils.isEmpty(algorithm)) {
				algorithm = "AES/CBC/PKCS5Padding";
			}

			AESCBCCoder aESCBCCoder = new AESCBCCoder(algorithm);

			aESCBCCoder.setKeySize(256);
			aESCBCCoder.setKeySeeds(keySeeds);

			aesCbcCoderMap.put(keySeeds, aESCBCCoder);
		}

		return aesCbcCoderMap.get(keySeeds).decryptStringFromString(data);
	}

//	public static void main(String[] args) {
//		AESCBCCoder enc = AESCBCCoderFactory.getAESCBCCoderObject(256, "0123456789ertyuiolkjhgfcvbnm",
//				null);
//		// enc.setKeySize(256);
//
//		// 加密密钥
//		// enc.setKeySeeds("0123456789ertyuiolkjhgfcvbnm");
//		// 待解密数据
//		String strData = "SF-EXPRESS:BSP2015-06-03 14:42:36";
//		long b1 = System.currentTimeMillis();
//		String s = enc.encryptToStringFromString(strData);
//		long b2 = System.currentTimeMillis();
//		String s2 = enc.decryptStringFromString(s);
//		long b3 = System.currentTimeMillis();
//		
//		System.out.println("第一次加密耗时:" + (b2-b1) + ";第一次解密耗时：" + (b3-b2));
//		System.out.println("原数据：" + strData + "\n加密结果：" + s);
//		System.out.println("解密结果：" + s2);
//		
//		long b4 = 0L;
//		
//		for (int i = 0; i < 1000; i++) {
//			b1 = System.currentTimeMillis();
//			s = enc.encryptToStringFromString(strData);
//			b2 = System.currentTimeMillis();
//			System.out.println(i + ":" + s);
//			b4 += b2-b1;
//		}
//		
//		System.out.println("后续使用缓存加密平均时间：" + b4/100);
//	}
}
