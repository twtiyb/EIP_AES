/* 
 * Copyright (c) 2014, S.F. Express Inc. All rights reserved.
 */
package com.sf.module.bspcommon.util.bspcodec;

import java.nio.charset.Charset;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * 描述：HMAC-SHA512工具，用于判断数据的完整性
 * 
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2014年10月20日      166046         Create
 * ****************************************************************************
 * </pre>
 * @author 166046
 */
public class HmacSha512Coder {
    private static final String algorithm = "HmacSHA512";
    private byte[] keySeeds;
    private final Charset charset = Charset.forName("UTF-8");
    
    public void setKeySeeds(String keeySeedsString) {
        this.keySeeds = new Base64Codec().encrypt(keeySeedsString);
    }

    public String generateHMAC(String datas) throws Exception {
        final SecretKeySpec secretKey = new SecretKeySpec(keySeeds, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKey);
        byte[] macData = mac.doFinal(datas.getBytes(charset));
        byte[] hashed = new Hex().encode(macData);
        return new String(hashed, charset);
    }
    
    public static void main(String[] args) throws Exception {
        HmacSha512Coder sha = new HmacSha512Coder();
        sha.setKeySeeds("123");
        String vStr = "EG4la/dSuBb+75/dfPp0ZjsbEjCl3+sQH2EHG7+kJOA=";
        System.out.println("原数据：" + vStr + "\nHMAC-SHA512:" + sha.generateHMAC(vStr));
    }

}
