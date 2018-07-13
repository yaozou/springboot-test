package com.yaozou.platform.common.aspect;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import javax.validation.ConstraintViolation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.yaozou.platform.common.domain.FieldError;
import com.yaozou.platform.common.exception.ParamValidException;
import org.hibernate.validator.internal.engine.path.PathImpl;

@Aspect
@Component
public class RequestParamValidAspect {

    private final ValidatorFactory    factory         = Validation.buildDefaultValidatorFactory();

    private final ExecutableValidator methodValidator = factory.getValidator().forExecutables();

    private final Validator           beanValidator   = factory.getValidator();

    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method,
                                                              Object[] params) {
        return methodValidator.validateParameters(obj, method, params);
    }

    private <T> Set<ConstraintViolation<T>> validBeanParams(T bean) {
        return beanValidator.validate(bean);
    }

    @Pointcut("execution(* com.yaozou.platform.member.controller.*.*(..))")
    public void soaControllerBefore() {

    }

    /* * 通过连接点切入 */
    @Before("soaControllerBefore()")
    public void before(JoinPoint point) throws ParamValidException, InterruptedException {
        // 获得切入目标对象
        Object target = point.getThis();
        // 获得切入方法参数
        Object[] args = point.getArgs();
        // 获得切入的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        // 校验以基本数据类型 为方法参数的
        Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, args);
        
        Iterator<ConstraintViolation<Object>> violationIterator = validResult.iterator();
        
        BlockingQueue<FieldError> errors = new ArrayBlockingQueue<FieldError>(10);

        while (violationIterator.hasNext()) {
            // 此处可以抛个异常提示用户参数输入格式不正确
            ConstraintViolation<Object> constraintViolation = violationIterator.next();
            PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
            FieldError error = new FieldError();
            // 获得校验的参数路径信息
            error.setName(pathImpl.getLeafNode().getName());
            // 获得校验的参数位置
            error.setMessage(constraintViolation.getMessage());
            errors.put(error);
        }

        // 校验以java bean对象 为方法参数的
        for (Object bean : args) {
            if (null != bean) {
                validResult = validBeanParams(bean);
                if (validResult.isEmpty())
                    continue;
                violationIterator = validResult.iterator();
                while (violationIterator.hasNext()) {
                    ConstraintViolation<Object> constraintViolation = violationIterator.next();
                    PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
                    FieldError error = new FieldError();
                    // 获得校验的参数路径信息
                    error.setName(pathImpl.getLeafNode().getName());
                    // 获得校验的参数位置
                    error.setMessage(constraintViolation.getMessage());
                    // 获得校验的参数名称
                    errors.put(error);
                }
            }
        }

        if (!errors.isEmpty())
            throw new ParamValidException(errors); // 我个人的处理方式，抛出异常，交给上层处理
    }

}