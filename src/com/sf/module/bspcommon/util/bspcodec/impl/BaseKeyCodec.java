package com.sf.module.bspcommon.util.bspcodec.impl;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.sf.module.bspcommon.util.bspcodec.Base64Codec;
import com.sf.module.bspcommon.util.bspcodec.BaseCodec;

/**
 * 对称性的加密解密处理类，支持多种算法: <br>
 * "DES","DESede"("TripleDES"), <br>
 * "AES","Blowfish","RC2", <br>
 * "RC4","ARCFOUR" <br>
 * 其特性是：根据唯一密钥，可以进行加解密
 * @author Liu YuCai
 * @version 1.0
 */
public class BaseKeyCodec extends BaseCodec {
    /**
     * 加解密的运算法则名称
     */    
	protected final String algorithm;

	protected byte[] keySeeds = null;
	
	/**
	 * 构造方法，参数为加解密的运算法则名称
	 * @param algorithm
	 */
	protected BaseKeyCodec(String algorithm){
        this.algorithm = algorithm;
    }
	
    public void setKeySeeds(String keeySeedsString) {
        this.keySeeds = keeySeedsString.getBytes(CHARSET);
    }

    /**
     * 根据从密钥获得的byte数组，产生一个用于加密解密的Key对象
     * @param key byte数组，从密钥获得，根据算法不同，数组大小不同
     * @return Key对象
     * @throws Exception 所用的算法名称为不支持的算法，则会抛出异常
     */
    protected Key toKey(byte[] key) throws Exception{
        return new javax.crypto.spec.SecretKeySpec(key, algorithm);
    }
    
    /**
     * 根据一个种子字节数组，创建一个密钥字节数组，
     * 如果种子字节数组为空，则随机创建一个
     * @param seeds，密钥种子数组，种子相同，产生的密钥相同
     * @return 密钥数组
     * @throws NoSuchAlgorithmException 如果构造函数所用的算法名称为不支持的算法，则会抛出异常
     * @throws UnsupportedEncodingException 
     */
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
        kg.init(secureRandom);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }
    
    /**
     * 加密方法，根据一个密钥，把输入的byte数组，进行加密并返回
     * @param data 需要加密的byte数组
     * @param keys 密钥字节数组
     * @return 加密后的byte数组
     * @throws Exception 所用的算法名称为不支持的算法，则会抛出异常
     */
    protected byte[] encrypt(byte[] data, byte[] keys) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, toKey(keys));
        return cipher.doFinal(data);
    }
    
    /**
     * 解密方法，根据密钥，把输入的byte数组，进行解密并返回
     * @param data 需要解密的byte数组，即，加密过后的byte数组
     * @param keys 密钥字节数组
     * @return 解密后的byte数组，即，原byte数组
     * @throws Exception 所用的算法名称为不支持的算法，则会抛出异常
     */
    protected byte[] decrypt(byte[] data, byte[] keys)throws Exception{
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, toKey(keys));
        return cipher.doFinal(data);
    }

    /**
     * @see com.sf.novatar.util.codec.BaseCodec#encrypt(byte[])
     */
    public byte[] encrypt(byte[] data){
        try {
            byte[] keySeeds = getKeySeeds();
            byte[] key = initKey(keySeeds);
            byte[] result = new Base64Codec().encrypt(encrypt(data, key));
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
    protected byte[] getKeySeeds() throws UnsupportedEncodingException {
        return this.keySeeds;
    }
    
    /**
     * @see com.sf.novatar.util.codec.BaseCodec#decrypt(byte[])
     */
    public byte[] decrypt(byte[] value) {
        try {
            byte[] keySeeds = getKeySeeds();
            byte[] key = initKey(keySeeds);
            byte[] result = decrypt(new Base64Codec().decrypt(value), key);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}