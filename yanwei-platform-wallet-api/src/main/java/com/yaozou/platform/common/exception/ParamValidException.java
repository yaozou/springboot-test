package com.yanwei.platform.common.exception;

import java.util.concurrent.BlockingQueue;
import com.yanwei.platform.common.domain.FieldError;

/**
 * 
 * 参数有错误
 * 
 * constraint  
@Null   被注释的元素必须为 null  
@NotNull    被注释的元素必须不为 null  
@AssertTrue     被注释的元素必须为 true  
@AssertFalse    被注释的元素必须为 false  
@Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值  
@Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值  
@DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值  
@DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值  
@Size(max=, min=)   被注释的元素的大小必须在指定的范围内  
@Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内  
@Past   被注释的元素必须是一个过去的日期  
@Future     被注释的元素必须是一个将来的日期  
@Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式    
Hibernate Validator 附加的 constraint  
@NotBlank(message =)   验证字符串非null，且长度必须大于0  
@Email  被注释的元素必须是电子邮箱地址  
@Length(min=,max=)  被注释的字符串的大小必须在指定的范围内  
@NotEmpty   被注释的字符串的必须非空  
@Range(min=,max=,message=)  被注释的元素必须在合适的范围内 
 * @author luojh
 *
 */

/**
 * 参数异常统一处理
 * @author luojianhong
 * @version $Id: ParamValidException.java, v 0.1 2017年11月23日 上午11:30:57 luojianhong Exp $
 */
public class ParamValidException extends RuntimeException {

    private static final long         serialVersionUID = 1L;
    
    private BlockingQueue<FieldError> fieldErrors;

    public ParamValidException(BlockingQueue<FieldError> errors) {
        this.fieldErrors = errors;
    }

    public BlockingQueue<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(BlockingQueue<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

}