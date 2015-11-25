package com.sf.module.bspcommon.util.bspcodec.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 * 对称性的加密解密处理类，算法为"AES"
 * @author Liu YuCai
 * @version 1.0
 */
public final class AESCodec extends BaseKeyCodec {
    private int keySize = 128;
    
	/**
	 * 构造方法，初始化算法名称
	 */
	public AESCodec(){
	    super("AES");
	}
	
    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }
    
    protected byte[] initKey(byte[] seeds) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (NoSuchProviderException e) {
            throw new Error(e);
        }
        if (seeds != null) {
            secureRandom.setSeed(seeds);
        }
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(keySize, secureRandom);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }
    
    public static void main(String[] args) throws Exception {
        AESCodec enc = new AESCodec();
        String seed = String.valueOf(Math.random());
        enc.setKeySize(256);
        enc.setKeySeeds(seed);
        String s = new String(enc.encryptToStringFromString("你好，我是密码！"));
        System.out.println(s);
        System.out.println(enc.decryptStringFromString(s));
    }
}