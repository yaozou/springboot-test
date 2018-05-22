package com.yanwei.platform.common.spring;

import java.lang.reflect.Method;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 重复提交检查器
 * @author luojianhong
 * @version $Id: SpringAvoidRepeatSubmitInterceptor.java, v 0.1 2017年3月16日 下午2:33:37 luojianhong Exp $
 */
public final class SpringRepeatSubmitInterceptor extends HandlerInterceptorAdapter {
    private static final String REQUEST_TOKEN = "repeatSubmitToken";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if ((handler instanceof HandlerMethod)) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            
            RepeatSubmitToken annotation = (RepeatSubmitToken) method
                .getAnnotation(RepeatSubmitToken.class);
            
            if (annotation != null) {
                boolean needSaveSession = annotation.addToken();
                if (needSaveSession) {
                    request.getSession(true).setAttribute(REQUEST_TOKEN,
                        UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.removeToken();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        return false;
                    }
                    request.getSession(true).removeAttribute(REQUEST_TOKEN);
                }
            }
            return true;
        }
        return super.preHandle(request, response, handler);
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(true).getAttribute(REQUEST_TOKEN);
        if (serverToken == null) {
            return true;
        }
        String clientToken = request.getParameter(REQUEST_TOKEN);
        if (clientToken == null) {
            return true;
        }
        if (!serverToken.equals(clientToken)) {
            return true;
        }
        return false;
    }
}