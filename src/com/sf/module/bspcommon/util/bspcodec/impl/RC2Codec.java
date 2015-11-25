package com.sf.module.bspcommon.util.bspcodec.impl;

/**
 * 对称性的加密解密处理类，算法为"RC2"
 * 
 * @author Liu YuCai
 * @version 1.0
 */
public class RC2Codec extends BaseKeyCodec {

    public RC2Codec() {
        super("RC2");
    }

    public static void main(String[] args) throws Exception {
        RC2Codec enc = new RC2Codec();
        String seed = String.valueOf(Math.random());
        enc.setKeySeeds(seed);
        String s = new String(enc.encryptToStringFromString("你好，我是密码！"));
        System.out.println(s);
        System.out.println(enc.decryptStringFromString(s));
    }
}