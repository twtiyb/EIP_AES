package com.sf.module.bspcommon.util.bspcodec;

import org.apache.commons.codec.binary.Base64;

/**
 * 普通的Base64加密解密算法
 * 
 * @author Liu YuCai
 * @version 1.0
 */
public class Base64Codec extends BaseCodec {

    /**
     * @see com.sf.novatar.util.codec.BaseCodec#encrypt(byte[])
     */
    @Override
    public byte[] encrypt(byte[] data) {
        return Base64.encodeBase64(data);
    }

    /**
     * @see com.sf.novatar.util.codec.BaseCodec#decrypt(byte[])
     */
    @Override
    public byte[] decrypt(byte[] value) {
        return Base64.decodeBase64(value);
    }

}