package com.qing.minisys.domain.page.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/**
 * 分页参数
 */
public @interface Pageable {

    /*
     * sqlId表示namespace.id
     */
    public String sqlId() default "";
    
    /*
     * 是否查询记录总数，默认不查询
     */
    public boolean isQueryTotal() default false;
    
    /*
     * 页码
     * @return
     */
    public String pageNo() default "";
    
    /*
     * 每页数据量
     * @return
     */
    public String pageSize() default "";
}
