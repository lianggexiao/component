package com.qing.minisys.domain.page;

import com.alibaba.fastjson.JSON;
import com.qing.minisys.domain.page.annotation.Pageable;
import com.qing.minisys.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 对业务层需要分页的方法进行拦截
 */
@Aspect
@Component
public class PageableInterceptor {

    private final Logger logger = LoggerFactory.getLogger(PageableInterceptor.class);

    /*
     * 需要拦截的方法
     */
    private final static String EXPRESS = "execution(* com.qing..service..*(..))";

    @Pointcut(EXPRESS)
    private void pointCut(){};

    @AfterReturning("pointCut()")
    public void afterReturning(JoinPoint joinPoint) {
        
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Pageable pageable = method.getAnnotation(Pageable.class);
        if(null != pageable) {
            PageContext pageContext = PageContext.getPageContext();
            pageContext.remove();
        }
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Pageable pageable = method.getAnnotation(Pageable.class);
        if(null != pageable) {
            if(logger.isDebugEnabled()) {
                logger.debug("call method [{}], params = {}", joinPoint.getSignature().toShortString(), JSON.toJSONString(joinPoint.getArgs()));
            }

            if(pageable.isQueryTotal()) {
                if(!method.getReturnType().equals(Page.class)) {
                    throw new RuntimeException("method [" + joinPoint.getSignature().toShortString() + "] should return type " + Page.class);
                }
            }
            
            //设置分页上下文
            PageContext pageContext = PageContext.getPageContext();
            pageContext.setPageable(true);
            pageContext.setSqlId(pageable.sqlId());
            pageContext.setQueryTotal(pageable.isQueryTotal());
            
            if(null != joinPoint.getArgs()) {
                //判断入参中是否包含Page对象，如果包含，就不需要在注解中获取pageNo和pageSize两个参数
                for(Object object : joinPoint.getArgs()) {
                    if(object instanceof Page<?>) {
                        pageContext.setPage((Page<?>) object);
                        break;
                    }
                }
            }
            
            if(null == pageContext.getPage()) {
                //入参中不包含Page对象
                String pageNoStr = pageable.pageNo();
                String pageSizeStr = pageable.pageSize();
                
                int pageNo = 0;
                if(StringUtils.isEmpty(pageNoStr)) {
                   throw new RuntimeException("parameter 'pageNo' in @Pageable of method [" + joinPoint.getSignature().toShortString() + "] is required");
                } else {
                    if(NumberUtils.isDigits(pageNoStr)) {
                        pageNo = Integer.parseInt(pageNoStr);
                    } else {
                        String[] expresses = parseExpress(pageNoStr);
                        int no = Integer.parseInt(StringUtils.substring(expresses[0], 1));
                        Object object = joinPoint.getArgs()[no];
                        pageNo = parseInt(object, expresses, 1);
                    }
                }
                int pageSize = 0;
                if(StringUtils.isEmpty(pageSizeStr)) {
                    throw new RuntimeException("parameter 'pageSize' in @Pageable of method [" + joinPoint.getSignature().toShortString() + "] is required");
                } else {
                    if(NumberUtils.isDigits(pageSizeStr)) {
                        pageSize = Integer.parseInt(pageSizeStr);
                    } else {
                        String[] expresses = parseExpress(pageSizeStr);
                        int no = Integer.parseInt(StringUtils.substring(expresses[0], 1));
                        Object object = joinPoint.getArgs()[no];
                        pageSize = parseInt(object, expresses, 1);
                    }
                }
                Page<?> page = new Page<>();
                page.setPageNo(pageNo);
                page.setPageSize(pageSize);
                pageContext.setPage(page);
            }
        }
    }
    
    private int parseInt(Object obj, String[] expresses, int index) {
        if(index == expresses.length) {
            return (int) obj;
        } else {
            String fieldName = expresses[index];
            return parseInt(ReflectionUtils.getFieldValue(obj, fieldName), expresses, index++);
        }
    }
    
    private String[] parseExpress(String value) {
        String regex = "^#p[0-9]*(\\.{1}[A-Za-z]{1}[A-Za-z0-9]*)*$";
        if(!value.matches(regex)) {
            throw new RuntimeException("express format error of @Pageable's parameter 'pageNo' and 'pageSize'");
        }
        value = StringUtils.substringAfter(value, "#");
        String[] strs = StringUtils.split(value, ".");
        return strs;
    }

    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void throwing(JoinPoint joinPoint, Exception e) {
        logger.error("call method [{}] error, error message is: {}", joinPoint.getSignature().toShortString(), e.getMessage());
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Pageable pageable = method.getAnnotation(Pageable.class);
        if(null != pageable) {
            PageContext pageContext = PageContext.getPageContext();
            pageContext.remove();
        }
    }
}
