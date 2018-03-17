package com.qing.minisys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ServletComponentScan   //扫描Servlet
@MapperScan("com.qing.minisys.repository")
public class MinisysApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinisysApplication.class, args);
	}
}
