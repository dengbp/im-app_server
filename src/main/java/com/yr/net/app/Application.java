package com.yr.net.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@ServletComponentScan
@EnableAsync
@EnableTransactionManagement
@MapperScan("com.yr.net.app.*.mapper")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//允许上传的文件最大值
		factory.setMaxFileSize(DataSize.ofBytes(100*1024*1024));
		/// 设置总上传数据总大小
		factory.setMaxRequestSize(DataSize.ofBytes(500*1024*1024));
		return factory.createMultipartConfig();
	}
}
