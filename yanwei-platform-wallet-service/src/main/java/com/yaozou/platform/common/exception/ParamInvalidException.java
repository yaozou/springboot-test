package com.yanwei.platform.common.exception;

 
/**
 * 参数错误异常
 * @author luojianhong
 * @version $Id: ParamInvalidException.java, v 0.1 2017年7月10日 下午5:21:27 luojianhong Exp $
 */
public class ParamInvalidException extends SystemException {
    private static final long   serialVersionUID   = 1L;
    /** 默认错误码. */
    private static final String DEFAULT_ERROR_CODE = "PARAM_INVALID";

    public ParamInvalidException(String errorMessage) {
        super(DEFAULT_ERROR_CODE, errorMessage);
    }

}
