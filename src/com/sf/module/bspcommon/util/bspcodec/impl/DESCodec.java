package com.sf.module.bspcommon.util.bspcodec.impl;

import java.security.Key;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * 对称性的加密解密处理类，算法为"DES"
 * @author Liu YuCai
 * @version 1.0
 */
public class DESCodec extends BaseKeyCodec {

	public DESCodec(){
        super("DES");
    }
	
    /**
     * @see com.sf.novatar.util.codec.impl.BaseKeyCodec#toKey(byte[])
     */
	@Override
    protected Key toKey(byte[] key) throws Exception{
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
        return keyFactory.generateSecret(dks);
    }
    
    public static void main(String[] args) throws Exception {
        DESCodec enc = new DESCodec();
        String seed = String.valueOf(Math.random());
        enc.setKeySeeds(seed);
        String s = new String(enc.encryptToStringFromString("你好，我是密码！"));
        System.out.println(s);
        System.out.println(enc.decryptStringFromString(s));
    }
}