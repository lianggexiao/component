package com.qing.minisys.interceptor;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogInterceptor {
    private final Logger logger = LoggerFactory.getLogger("web_log");

    @Pointcut("execution(* com.qing.minisys.service..*(..))")
    private void pointCut(){};

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        if(logger.isInfoEnabled()) {
            logger.info("开始调用方法[{}], 方法参数：{}", joinPoint.getSignature().toShortString(), JSON.toJSONString(joinPoint.getArgs()));
        }
    }

    @AfterReturning("pointCut()")
    public void afterReturning(JoinPoint joinPoint) {
        if(logger.isInfoEnabled()) {
            logger.info("完成调用方法[{}]", joinPoint.getSignature().toShortString());
        }
    }

    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void throwing(JoinPoint joinPoint, Exception e) {
        logger.error("调用方法[{}]异常，异常信息为：{}", joinPoint.getSignature().toShortString(), e.getMessage(), e);
    }

}
