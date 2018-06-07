package com.yaozou.platform.common.aspect;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbus.net.http.Message;
import com.alibaba.fastjson.JSON;
import com.yaozou.platform.common.annotation.Log;
import com.yaozou.platform.common.domain.SysLogDO;
import com.yaozou.platform.common.utils.ConvertUtils;
import com.yaozou.platform.common.utils.HttpContextUtils;
import com.yaozou.platform.common.utils.JSONUtils;
import com.yaozou.platform.common.zbus.ZbusProducerLogService;

/**
 * 系统操作日志记录器
 * @author luojianhong
 * @version $Id: LogAspect.java, v 0.1 2017年10月9日 下午4:42:53 luojianhong Exp $
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private ZbusProducerLogService zbusProducerLogService;

    @Pointcut("@annotation(com.yaozou.platform.common.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            Object result = point.proceed();
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            // 保存日志
            if(result!=null)
               saveLog(point, time, JSONUtils.beanToJson(result));
            else {
                saveLog(point, time, "返回结果为空");
            }
            return result;
        } catch (Throwable t) {
            long time = System.currentTimeMillis() - beginTime;
            saveLog(point, time, t.getMessage());
            throw t;
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time, String result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLogDO sysLog = new SysLogDO();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            if(args.length>0) {
            String params = JSONUtils.beanToJson(args[0]);
            sysLog.setParams(params);
            }
            if (result.length() < 5000000)
                sysLog.setResult(result);
            else
                sysLog.setResult("返回结果太长不记录");
        } catch (Exception e) {

        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(ConvertUtils.getIpAddrByRequest(request));
        // 用户名
        sysLog.setTime(time);
        sysLog.setCreateTime(new Date());
        sysLog.setUserId(HttpContextUtils.getTokenModel().getUserId());
        sysLog.setUserName(HttpContextUtils.getTokenModel().getUserName());
        //把日志信息丢到mq队列，由换日志服务进行异步消费处理
        try {
			zbusProducerLogService.sendAsync(new Message().setJsonBody(JSON.toJSONString(sysLog)));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
