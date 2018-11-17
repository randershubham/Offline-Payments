package com.bits.offline.payments.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.bits.offline.payments")
public class BackendApplication {

	public static void main(String[] args) {
		System.out.println("Application Started");
		SpringApplication.run(BackendApplication.class, args);
	}
}
