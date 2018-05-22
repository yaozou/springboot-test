package com.yanwei.platform.common.exception;

/**
 * 远程调用异常
 * @author luojianhong
 * @version $Id: FacadeInvokeFailException.java, v 0.1 2017年7月10日 下午5:21:07 luojianhong Exp $
 */
public class FacadeInvokeFailException extends SystemException {
    private static final long serialVersionUID = 1L;

    public FacadeInvokeFailException() {
        super("远程调用失败");
    }

    public FacadeInvokeFailException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

}
