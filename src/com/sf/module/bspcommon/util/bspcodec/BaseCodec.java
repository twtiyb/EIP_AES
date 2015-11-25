package com.sf.module.bspcommon.util.bspcodec;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;


/**
 * 加密解密对象基类，提供对8种基础数据类型数据的解密和解密接口
 * @author Liu YuCai
 * @version 1.0
 */
public abstract class BaseCodec {

    /** 字符串处理默认字符集 */
    protected final static Charset CHARSET = Charset.forName("UTF-8");
    
	/**
	 * 加密byte数组对象
	 * @param data
	 */
	public abstract byte[] encrypt(byte[] data);

	/**
	 * 解密byte数组对象
	 * @param value
	 */
	public abstract byte[] decrypt(byte[] value);
	
	/**
	 * 加密 byte值，成为byte数组对象
	 * @param data
	 */
	public final byte[] encrypt(byte data){
		return encrypt(new byte[]{data});
	}

	/**
	 * 加密 short值，成为byte数组对象
	 * @param data
	 */
	public final byte[] encrypt(short data){
	    ByteBuffer buffer = ByteBuffer.allocate(2);
	    buffer.putShort(data);
	    return encrypt(buffer.array());
	}

	/**
	 * 加密 int值，成为byte数组对象
	 * @param data
	 */
	public final byte[] encrypt(int data){
	    ByteBuffer buffer = ByteBuffer.allocate(4);
	    buffer.putInt(data);
	    return encrypt(buffer.array());
	}

	/**
	 * 加密 long值，成为byte数组对象
	 * @param data
	 */
	public final byte[] encrypt(long data){
	    ByteBuffer buffer = ByteBuffer.allocate(8);
	    buffer.putLong(data);
	    return encrypt(buffer.array());
	}

	/**
	 * 加密 float值，成为byte数组对象
	 * @param data
	 */
	public final byte[] encrypt(float data){
	    ByteBuffer buffer = ByteBuffer.allocate(4);
	    buffer.putFloat(data);
	    return encrypt(buffer.array());
	}

	/**
	 * 加密 double值，成为byte数组对象
	 * @param data
	 */
	public final byte[] encrypt(double data){
	    ByteBuffer buffer = ByteBuffer.allocate(8);
	    buffer.putDouble(data);
	    return encrypt(buffer.array());
	}
    
	/**
	 * 加密 String字符串值，成为byte数组对象
	 * @param data
	 */
    public final byte[] encrypt(String data) {
        return encrypt(data.getBytes(CHARSET));
    }

    /**
     * 加密
     * @param data
     * @return
     */
    public String encryptToStringFromString(String data) {
        return new String(encrypt(data), CHARSET);
    }

    public String encryptToString(byte[] data) {
        return new String(encrypt(data), CHARSET);
    }

	/**
	 * 加密 boolean值，成为byte数组对象，由于boolean只有两种值状态，所以需要特殊处理
	 * 为true则随机产生一个特征数据，进行加密，为false则随机产生另外一个相对的特征数据进行加密
	 * @param data
	 */
	public final byte[] encrypt(boolean data){
        byte val = (byte) new Random().nextInt(Byte.MAX_VALUE - 1);
        return encrypt((byte) (data ? val + 1 : -val - 1));
	}

	/**
	 * 解密byte加密后的byte数组
	 * @param value
	 */
	public final byte decryptByte(byte[] value){
	    ByteBuffer buffer = ByteBuffer.wrap(decrypt(value));
		return buffer.get();
	}

    public byte[] decryptByteFromString(String value) {
        return decrypt(value.getBytes(CHARSET));
    }
    
	/**
	 * 解密short加密后的byte数组
	 * @param value
	 */
	public final short decryptShort(byte[] value){
	    ByteBuffer buffer = ByteBuffer.wrap(decrypt(value));
		return buffer.getShort();
	}

	/**
	 * 解密int加密后的byte数组
	 * @param value
	 */
	public final int decryptInt(byte[] value){
	    ByteBuffer buffer = ByteBuffer.wrap(decrypt(value));
		return buffer.getInt();
	}

	/**
	 * 解密long加密后的byte数组
	 * @param value
	 */
	public final long decryptLong(byte[] value){
	    ByteBuffer buffer = ByteBuffer.wrap(decrypt(value));
		return buffer.getLong();
	}

	/**
	 * 解密float加密后的byte数组
	 * @param value
	 */
	public final float decryptFloat(byte[] value){
	    ByteBuffer buffer = ByteBuffer.wrap(decrypt(value));
		return buffer.getFloat();
	}

	/**
	 * 解密double加密后的byte数组
	 * @param value
	 */
	public final double decryptDouble(byte[] value){
	    ByteBuffer buffer = ByteBuffer.wrap(decrypt(value));
		return buffer.getDouble();
	}

	/**
	 * 解密String加密后的byte数组
	 * @param value
	 */
    public final String decryptString(byte[] value) {
        return new String(decrypt(value), CHARSET);
    }

    /**
     * 解密
     * @param value
     * @return
     */
    public String decryptStringFromString(String value) {
        return decryptString(value.getBytes(CHARSET));
    }
	   
	/**
	 * 解密boolean加密后的byte数组
	 * @param value
	 */
	public final boolean decryptBoolean(byte[] value){
		return decryptByte(value) > 0;
	}

}