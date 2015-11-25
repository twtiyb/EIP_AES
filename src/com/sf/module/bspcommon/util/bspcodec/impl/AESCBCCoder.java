/* 
 * Copyright (c) 2014, S.F. Express Inc. All rights reserved.
 */
package com.sf.module.bspcommon.util.bspcodec.impl;

import java.security.Key;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * 描述：
 * 
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2014年10月20日      166046         Create
 * ****************************************************************************
 * </pre>
 * @author 166046
 */
public class AESCBCCoder extends BaseKeyCodec {
    private int keySize = 256;
    
    public AESCBCCoder() {
        super("AES/CBC/PKCS5Padding");
//    	super("AES/CBC/NoPadding");
    }
    
    public AESCBCCoder(String algorithm) {
        super(algorithm);
    }
    
    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }
    
    protected byte[] initKey(byte[] seeds) throws Exception {
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
    
    protected Key toKey(byte[] key) throws Exception {
        return new javax.crypto.spec.SecretKeySpec(key, "AES");
    }
    
    protected byte[] decrypt(byte[] data, byte[] keys) throws Exception {
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, toKey(keys), iv);
        return cipher.doFinal(data);
    }
    
    protected byte[] encrypt(byte[] data, byte[] keys) throws Exception {
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, toKey(keys), iv);
        return cipher.doFinal(data);
    }
    
    public static void main(String[] args) throws Exception {
        AESCBCCoder enc = new AESCBCCoder();
        enc.setKeySize(256);
        
        //加密密钥
        enc.setKeySeeds("1234567890poiuytgfdswaertmqdswqa");
        //待解密数据
        String strData="SF-EXPRESS:BSP20150602110509";
        String s = new String(enc.encryptToStringFromString(strData));
        System.out.println("原数据：" + strData + "\n加密结果：" + s);
        System.out.println("解密结果：" + enc.decryptStringFromString(s));
    }

}
