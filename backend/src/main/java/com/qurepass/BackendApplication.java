package com.qurepass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.qurepass.mapper")
@SpringBootApplication(scanBasePackages = { "com.qurepass.config", "com.qurepass.service", "com.qurepass.controller" })
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
