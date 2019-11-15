package com.cmpe275.openhome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OpenhomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenhomeApplication.class, args);
	}
}
