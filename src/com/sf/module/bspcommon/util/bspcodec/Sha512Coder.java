/* 
 * Copyright (c) 2014, S.F. Express Inc. All rights reserved.
 */
package com.sf.module.bspcommon.util.bspcodec;

import java.nio.charset.Charset;

import org.apache.commons.codec.digest.Sha2Crypt;

/**
 * 描述：带salt的SHA512用于对数据进行hash
 * 
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2014年10月20日      166046         Create
 * ****************************************************************************
 * </pre>
 * @author 166046
 */
public class Sha512Coder {
    private final Charset charset = Charset.forName("UTF-8");

    private String saltString;

    public void setSalt(String saltString) {
        this.saltString = saltString;
    }

    public String generaterHash(String datas) {
        return Sha2Crypt.sha512Crypt(datas.getBytes(charset), saltString);
    }

    public static void main(String[] args) {
        Sha512Coder coder = new Sha512Coder();
        coder.setSalt("$6$0123456789");
        System.out.println(coder.generaterHash("this is a test data for hash."));
    }
}
