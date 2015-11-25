package com.sf.module.bspcommon.util.bspcodec;

import java.util.HashMap;
import java.util.Map;

/**
 * 按加密密钥进行缓存HmacSha512Coder对象，提高频繁使用时的性能
 * @author 056235
 *
 */
public class HmacSha512CoderFactory {
	static Map<String, HmacSha512Coder> hmacSha512CoderMap = new HashMap<String, HmacSha512Coder>();

	public static HmacSha512Coder getAESCBCCoderObject(String keySeeds) {
		if (null == hmacSha512CoderMap.get(keySeeds)) {

			HmacSha512Coder hmacSha512Coder = new HmacSha512Coder();

			hmacSha512Coder.setKeySeeds(keySeeds);

			hmacSha512CoderMap.put(keySeeds, hmacSha512Coder);
		}

		return hmacSha512CoderMap.get(keySeeds);
	}
	public static String getHmacSha512Coder(String keySeeds,String data) {
		if (null == hmacSha512CoderMap.get(keySeeds)) {

			HmacSha512Coder hmacSha512Coder = new HmacSha512Coder();

			hmacSha512Coder.setKeySeeds(keySeeds);

			hmacSha512CoderMap.put(keySeeds, hmacSha512Coder);
		}
        String Hdata;
		try {
			Hdata= hmacSha512CoderMap.get(keySeeds).generateHMAC(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return Hdata;
		
	}
	public static void main(String[] args) throws Exception {
		long b1 = System.currentTimeMillis();
		HmacSha512Coder sha = HmacSha512CoderFactory.getAESCBCCoderObject("Ra4jMmdoeVhSSiZkVHUV8Ym/VMEHFXBK");
        String vStr = "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";
        String generateHMAC = sha.generateHMAC(vStr);
        long b2 = System.currentTimeMillis();
        
		System.out.println("原数据：" + vStr + "\nHMAC-SHA512:" + generateHMAC);		
		System.out.println("第1次:" + (b2-b1));
		
		b1 = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			sha.generateHMAC(vStr);
		}
		b2 = System.currentTimeMillis();
		System.out.println("后续100次:" + (b2-b1));
	}
}
