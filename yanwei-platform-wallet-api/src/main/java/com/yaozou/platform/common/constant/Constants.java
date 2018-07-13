package com.yaozou.platform.common.constant;

/**
 * 常量
 * @author luojianhong
 * @version $Id: Constants.java, v 0.1 2017年10月13日 上午10:20:34 luojianhong Exp $
 */
public abstract class Constants {

    /**
     * API_VER 版本
     */
    public final static String API_VER               = "api-ver=1.0.0";
    
    public final static String API_VERSION               = "1.0.0";

    public final static String API_VER_1               = "api-ver=1.0.1";
    public final static String API_VERSION_1             = "1.0.1";

    /**
    * 存储当前登录用户id的字段名 
    */
    public static final String CURRENT_USER       = "CURRENT_USER";
    /** 
     * token有效期（小时） 
     */
    public static final int    TOKEN_EXPIRES_HOUR    = 30 * 1 * 60 * 60; //天时分秒 
    

    public static final int    TOKEN_EXPIRES_SECONDS = 7 * 24 * 60 * 60 * 1000;//天时分秒 

    /** 
     * 存放Authorization的header字段 
     */
    public static final String AUTHORIZATION  = "token";
    
    public static final String TOKEN_INVALID_MESSAGE="令牌无效";
    
    public static final Integer TOKEN_INVALID_CODE=922999;
}