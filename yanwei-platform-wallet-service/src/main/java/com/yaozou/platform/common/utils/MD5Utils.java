package com.yanwei.platform.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 加密类
 * @author luojianhong
 * @version $Id: MD5Utils.java, v 0.1 2017年10月10日 上午11:16:00 luojianhong Exp $
 */
public class MD5Utils {
    
	//private static final String SALT = "1qazxsw2";

	private static final String ALGORITH_NAME = "md5";
	
	private static final String SALT = "btcmsxtgl01";

	private static final int HASH_ITERATIONS = 2;

	public static String encrypt(String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static String encrypt(String username, String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + SALT),
				HASH_ITERATIONS).toHex();
		return newPassword;
	}
	public static void main(String[] args) {
	    System.out.println(MD5Utils.encrypt("admin", "111111"));
		///System.out.println(MD5Utils.encrypt("admin", "111111"));
		//27bd386e70f280e24c2f4f2a549b82cf SALT = "1qazxsw2";
	    //7cea560f4888a122f4b51d2a97eb9bcd SALT = "btcmsxtgl01";
	}

}
