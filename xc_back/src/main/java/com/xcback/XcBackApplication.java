package com.xcback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.xcback.admin.dao")
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class XcBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(XcBackApplication.class, args);
	}

}

