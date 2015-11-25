package com.sf.module.bspcommon.util.bspcodec.impl;

import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 对称性的加密解密处理类，算法为"RC4"
 * @author Liu YuCai
 * @version 1.0
 */
public class RC4Codec extends BaseKeyCodec {
    protected int keySize = 128;
    
	public RC4Codec(){
        super("RC4");
    }
	
    public String encrypt(String username, String password) {
        RC4Codec enc = new RC4Codec();
        enc.setKeySeeds(password);
        return enc.encryptToStringFromString(username + password);
    }
    
    public int getKeySize() {
        return keySize;
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
        KeyGenerator kg = KeyGenerator.getInstance(algorithm);
        kg.init(keySize, secureRandom);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }
    
	public static void main(String[] args) throws Exception {
	    String username = "admin";
	    String password = "monkey";
	    RC4Codec enc = new RC4Codec();
        enc.setKeySeeds(password);
        String s = enc.encryptToStringFromString(username + password);
        System.out.println(s);
        System.out.println(enc.decryptStringFromString(s));
        
        username = "owner";
        password = "111111";
        enc = new RC4Codec();
        enc.setKeySeeds(password);
        System.out.println(enc.encryptToStringFromString(username + password));
        
        username = "166046";
        password = "111111";
        enc = new RC4Codec();
        enc.setKeySeeds(password);
        System.out.println(enc.encryptToStringFromString(username + password));
    }
}