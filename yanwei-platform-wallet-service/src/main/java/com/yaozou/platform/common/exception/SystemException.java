package com.yanwei.platform.common.exception;

import java.util.LinkedList;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统异常.为运行期异常增加异常代码与异常信息
 * @author luojianhong
 * @version $Id: SystemException.java, v 0.1 2017年7月10日 下午5:21:41 luojianhong Exp $
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemException extends RuntimeException {

    private static final long    serialVersionUID      = 1L;
    /** 默认错误码. */
    private static final String  DEFAULT_ERROR_CODE    = "UNKNOWN";
    /** 默认错误消息. */
    private static final String  DEFAULT_ERROR_MESSAGE = "未声明异常";
    /** 错误码. */
    protected String             errorCode;
    /** 错误消息. */
    protected String             errorMessage;
    /** 异常路径. */
    protected LinkedList<String> errorPath;

    public SystemException() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public SystemException(String errorMessage) {
        this(DEFAULT_ERROR_CODE, errorMessage);
    }

    public SystemException(String errorCode, String errorMessage) {
        this(errorCode, errorMessage, (Throwable) null);
    }

    public SystemException(Throwable cause) {
        this(DEFAULT_ERROR_MESSAGE, cause);
    }

    public SystemException(String errorMessage, Throwable cause) {
        this(DEFAULT_ERROR_CODE, errorMessage, cause);
    }

    public SystemException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public SystemException(String errorCode, String errorMessage, LinkedList<String> errorPath) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorPath = errorPath;
    }
}
