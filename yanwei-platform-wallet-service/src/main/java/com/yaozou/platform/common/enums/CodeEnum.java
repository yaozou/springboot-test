package com.yaozou.platform.common.enums;


/**
 * API状态码
 */
public enum CodeEnum {
    SUCCESS(200, "SUCCESS"),
    FAILURE(501, "FAILURE"),
    SYSTEM_ERROR(500, "系统错误"),
    ;

    private int code;
    
    private String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
