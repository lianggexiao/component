package com.qing.simpleframe.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.qing.simpleframe.interceptor.MDCInterceptor;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * 注册自定义拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(mdcInterceptor());
//		registry.addInterceptor(sqlInjectInterceptor());
	}
	
	@Bean
    public MDCInterceptor mdcInterceptor() {
        return new MDCInterceptor();
    }
	
	
	//启用Spring-driven Method Validation
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {  
	  return new MethodValidationPostProcessor();
	}
}
