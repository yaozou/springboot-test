package com.yaozou.platform.common.enums;

 
/**
 *  API状态码
 * @author luojianhong
 * @version $Id: CodeEnum.java, v 0.1 2017年11月28日 下午1:47:10 luojianhong Exp $
 */
public enum CodeEnum {
    SUCCESS(200, "SUCCESS"),
    FAILURE(501, "FAILURE"),
    SYSTEM_ERROR(500, "系统错误"),
    DUPLICATE_ERROR(501, "重复操作"),
    NON_EXIST_ERROR(502, "操作的数据不存在"),
    NULL_ERROR(503, "操作的数据为空"),
    NOT_ENOUGH(504, "数量不足"),
    PARAM_ERROR(505, "参数错误"),
    
    NOT_SCORE(506, "充许使用惠币,数量不正确"),
    NOT_EMPTY_SCORE(507, "未传入有效的,惠币数量"),
    NOT_FOUND_SCORE(508, "未查找到,使用惠币的帐户"),
     
    USER_NOT_LOGIN(10001,"用户Id不存在"),
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
