package com.yanwei.platform.common.domain;

import java.io.Serializable;
import java.util.LinkedList;
import lombok.Data;

/**
 * facade接口调用结果包装器
 * @author luojianhong
 * @version $Id: Result.java, v 0.1 2017年7月10日 下午5:23:06 luojianhong Exp $
 */

@Data
public class Result implements Serializable {

    private static final long     serialVersionUID          = 1L;
    /** 默认错误码. */
    protected static final String DEFAULT_EXCEPTION_CODE    = "UNDEFINED_ERROR";
    /** 默认错误消息. */
    protected static final String DEFAULT_EXCEPTION_MESSAGE = "系统异常";
    /** 异常提示信息（成功请求返回为空）. */
    protected String              errorMessage;
    /** 异常代码（成功请求返回为空）. */
    protected String              errorCode;
    /** 异常路径. */
    protected LinkedList<String>  errorPath;

    /** 当前执行结果是否成功. */
    public boolean isSuccess() {
        return null == errorCode;
    }

    /**
     * 添加当前系统路径.
     * 
     * @param systemCode
     *            系统编码
     * @param interfaceCode
     *            接口编码
     */
    public void addErrorPath(String systemCode, String interfaceCode) {
        if (errorPath == null) {
            errorPath = new LinkedList<>();
        }
        errorPath.addFirst(systemCode + ":" + interfaceCode);
    }

}
