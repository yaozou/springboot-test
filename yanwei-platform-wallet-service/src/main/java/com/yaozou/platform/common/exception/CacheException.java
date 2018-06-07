 package com.yaozou.platform.common.exception;

 
/**
 * 分布式缓存异常类
 * @author luojianhong
 * @version $Id: CacheException.java, v 0.1 2017年7月10日 下午5:20:51 luojianhong Exp $
 */
public class CacheException extends SystemException {
    private static final long   serialVersionUID   = 1L;
    /** 默认错误码. */
    private static final String DEFAULT_ERROR_CODE = "CACHE_EXCEPTION";

    public CacheException(String errorMessage) {
        super(DEFAULT_ERROR_CODE, errorMessage);
    }

    public CacheException(String errorMessage, Throwable cause) {
        super(DEFAULT_ERROR_CODE, errorMessage, cause);
    }
}
