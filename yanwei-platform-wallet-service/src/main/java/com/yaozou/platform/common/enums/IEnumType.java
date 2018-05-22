package com.yanwei.platform.common.enums;

/**
 * 为了能统一处理数据库中定义的各种状态，状态枚举都需要实现此接口
 * @author yaozou
 *
 */
public interface IEnumType {
	 /**
     * 数据库中定义的 数字 状态码
     */
    int code();

    /**
     * 简单描述
     */
    String desc();
}
