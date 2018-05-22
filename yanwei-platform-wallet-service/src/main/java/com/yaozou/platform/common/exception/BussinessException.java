package com.yanwei.platform.common.exception;

 
/**
 * 业务异常
 * @author luojianhong
 * @version $Id: BussinessException.java, v 0.1 2017年7月10日 下午5:20:41 luojianhong Exp $
 */
public class BussinessException extends SystemException {
    private static final long serialVersionUID = 1L;

    public BussinessException(String errorMessage) {
        super(errorMessage);
    }

    public BussinessException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public BussinessException(Throwable cause) {
        super(cause);
    }

    public BussinessException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public BussinessException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

}
