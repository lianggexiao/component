package com.qing.minisys.core;

import com.qing.minisys.interceptor.WebLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.qing.minisys.interceptor.MDCInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment environment;

	/**
	 * 注册自定义拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(mdcInterceptor());
		registry.addInterceptor(logInterceptor());
//		registry.addInterceptor(sqlInjectInterceptor());
	}
	
	@Bean
    public MDCInterceptor mdcInterceptor() {
        return new MDCInterceptor();
    }

	@Bean
	public WebLogInterceptor logInterceptor() {
		return new WebLogInterceptor();
	}

	//启用Spring-driven Method Validation
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {  
	  return new MethodValidationPostProcessor();
	}

	/**
	 * 配置验签过滤器
	 * @return
	 */
//	@Bean
//	public FilterRegistrationBean someFilterRegistration() {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(new ParameterWrapperFilter(environment));
//		registration.addUrlPatterns("/public/*");
//		registration.setName("parameterFilter");
//		registration.setOrder(1);
//		return registration;
//	}
}
