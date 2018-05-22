package com.yanwei.platform.common.domain;

import com.yanwei.platform.common.exception.SystemException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * facade接口调用结果包装器.
 * 
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FacadeInvokeDataResult<T> extends Result {
    private static final long serialVersionUID = 1L;

    /** 结果数据. */
    protected T               data;

    /**
     * 获取成功且不带数据的结果实例.
     * 
     * @return 结果对象
     */
    public static <T> FacadeInvokeDataResult<T> success() {
        FacadeInvokeDataResult<T> result = new FacadeInvokeDataResult<>();
        return result;
    }

    /**
     * 获取成功且带数据的结果实例.
     * 
     * @param data
     *            结果数据
     * @return 结果对象
     */
    public static <T> FacadeInvokeDataResult<T> success(T data) {
        FacadeInvokeDataResult<T> result = new FacadeInvokeDataResult<T>();
        result.data = data;
        return result;
    }

    /**
     * 获取指定异常代码和消息的失败结果实例.
     * 
     * @param errorCode
     *            错误码
     * @param errorMessage
     *            错误消息
     * @return 结果对象
     */
    public static <T> FacadeInvokeDataResult<T> failure(String errorCode, String errorMessage) {
        FacadeInvokeDataResult<T> result = new FacadeInvokeDataResult<>();
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
     * 
     * @param errorMessage
     * @return 结果对象
     */
    public static <T> FacadeInvokeDataResult<T> failure(String errorMessage) {
        FacadeInvokeDataResult<T> result = new FacadeInvokeDataResult<>();
        result.errorCode = DEFAULT_EXCEPTION_CODE;
        result.errorMessage = errorMessage;
        return result;
    }

    /**
     * 获取指定异常的失败结果实例 如果异常为SystemException,则直接使用异常代码与消息;
     * 否则异常代码为UNDEFINED_ERROR,异常消息为:"系统异常".
     * 
     * @param t
     *            异常
     * @return 结果对象
     */
    public static <T> FacadeInvokeDataResult<T> failure(Throwable t) {
        FacadeInvokeDataResult<T> result = new FacadeInvokeDataResult<>();
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

    /**
     * 获取指定异常的失败结果实例 
     * 如果异常为SystemException,则直接使用异常代码与消息;
     * 否则异常代码为UNDEFINED_ERROR,异常消息为:"系统异常".
     * 
     * @param errorCode
     *            错误码
     * @param errorMessage
     *            错误消息
     * @return 结果对象
     */
    public static <T> FacadeInvokeDataResult<T> failure(String errorCode, String errorMessage,
                                                        T data) {
        FacadeInvokeDataResult<T> result = new FacadeInvokeDataResult<>();
        result.errorCode = errorCode;
        result.errorMessage = errorMessage;
        result.data = data;
        return result;
    }

}