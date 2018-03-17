package com.qing.minisys.interceptor;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc 请求日志打印，在每个http请求前，和请求响应（或异常）后，均打印日志，记录这个请求的入参、出参、异常信息
 */
@Aspect
public class WebLogInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger("web_log");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(logger.isInfoEnabled()) {
            logger.info("请求路径：{}，请求参数：{}", request.getRequestURI(), request.getParameterMap());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(logger.isInfoEnabled()) {
            logger.info("完成请求：{}", request.getRequestURI());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(ex != null) {
            logger.error("请求{}异常，异常信息为：{}", request.getRequestURI(), ex.getMessage(), ex);
        }
        //FIXME 此处可以根据我们的异常体系，通用处理异常，并发挥统一的异常信息
    }
}
