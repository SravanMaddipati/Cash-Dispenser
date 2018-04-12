package com.cash.dispenser.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cash.dispenser")
public class CashdispenserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashdispenserApplication.class, args);
	}
}