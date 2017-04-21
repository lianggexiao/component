package com.qing.simpleframe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@ServletComponentScan   //扫描Servlet
@MapperScan("com.qing.simpleframe.repository")
public class SimpleframeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleframeApplication.class, args);
	}
}
