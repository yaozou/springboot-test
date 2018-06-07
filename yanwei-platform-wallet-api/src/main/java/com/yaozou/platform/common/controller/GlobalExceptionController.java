package com.yaozou.platform.common.controller;

import com.yaozou.platform.common.enums.CodeEnum;
import com.yaozou.platform.common.exception.MembersException;
import com.yaozou.platform.common.exception.ParamValidException;
import com.yaozou.platform.member.domain.ApiOut;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 字段验证通用方法类
 * @author luojianhong
 * @version $Id: GlobalExceptionController.java, v 0.1 2017年11月23日 上午9:02:28 luojianhong Exp $
 */
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = Exception.class)
    public ApiOut<Object> errorHandler(Exception ex) {
        int code;
        String msg;
        if (ex instanceof MembersException) {

            code = ((MembersException) ex).getCode();
            msg = ex.getMessage();

        } else if (ex instanceof ParamValidException) {

            code = CodeEnum.PARAM_ERROR.getCode();
            msg = CodeEnum.PARAM_ERROR.getMsg();

            ApiOut<Object> apiOut = ApiOut.failure(code, msg);
            apiOut.setData(((ParamValidException) ex).getFieldErrors());

            return apiOut;
        } else {
            code = CodeEnum.SYSTEM_ERROR.getCode();
            msg = CodeEnum.SYSTEM_ERROR.getMsg();
        }

        if (ex != null) {
            ex.printStackTrace();
            System.out.println("系统出现异常了:" + ex.getMessage());
        }

        return ApiOut.failure(code, msg);
    }

}
