package com.qing.simpleframe.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MDCInterceptor extends HandlerInterceptorAdapter {

    private final static String SESSION_ID = "sessionId";
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = UUID.randomUUID().toString();
        MDC.put(SESSION_ID, requestId);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(SESSION_ID);
        super.afterCompletion(request, response, handler, ex);
    }
}
