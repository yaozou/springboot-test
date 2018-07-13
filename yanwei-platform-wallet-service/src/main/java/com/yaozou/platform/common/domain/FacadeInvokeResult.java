package com.yaozou.platform.common.domain;

import com.yaozou.platform.common.exception.SystemException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * facade接口调用结果包装器.
 * @author luojianhong
 * @version $Id: FacadeInvokeResult.java, v 0.1 2017年7月10日 下午5:23:41 luojianhong Exp $
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FacadeInvokeResult extends Result {
    private static final long serialVersionUID = 1L;

    /** 
     * 获取成功且不带数据的结果实例.
     * @return 结果对象
     */
    public static FacadeInvokeResult success() {
        return new FacadeInvokeResult();
    }

    /**
     * 获取指定异常代码和消息的失败结果实例.
     * @param errorCode 错误码
     * @param errorMessage 错误消息
     * @return 结果对象
     */
    public static FacadeInvokeResult failure(String errorCode, String errorMessage) {
        FacadeInvokeResult result = new FacadeInvokeResult();
        if (errorCode != null && errorCode.trim().length() > 0) {
            result.errorCode = errorCode;
        } else {
            result.errorCode = DEFAULT_EXCEPTION_CODE;
        }
        result.errorMessage = errorMessage;
        return result;
    }

    /**
     * 获取指定异常代码和消息的失败结果实例.
     * @param errorMessage
     * @return 结果对象
     */
    public static FacadeInvokeResult failure(String errorMessage) {
        FacadeInvokeResult result = new FacadeInvokeResult();
        result.errorCode = DEFAULT_EXCEPTION_CODE;
        result.errorMessage = errorMessage;
        return result;
    }

    /**
     * 获取指定异常的失败结果实例 如果异常为SystemException,则直接使用异常代码与消息;
     * 否则异常代码为UNDEFINED_ERROR,异常消息为:"系统异常".
     * @param t 异常
     * @return 结果对象
     */
    public static FacadeInvokeResult failure(Throwable t) {
        FacadeInvokeResult result = new FacadeInvokeResult();
        if (t instanceof SystemException) {
            SystemException se = (SystemException) t;
            result.errorCode = se.getErrorCode();
            result.errorMessage = se.getErrorMessage();
            result.errorPath = se.getErrorPath();
        } else {
            result.errorCode = DEFAULT_EXCEPTION_CODE;
            result.errorMessage = DEFAULT_EXCEPTION_MESSAGE;
        }
        return result;
    }
}