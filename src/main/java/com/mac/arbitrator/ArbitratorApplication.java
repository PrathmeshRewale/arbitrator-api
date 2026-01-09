package com.mac.arbitrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ArbitratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArbitratorApplication.class, args);
	}

}
