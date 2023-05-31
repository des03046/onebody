package com.telefield.onebody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OnebodyApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnebodyApplication.class, args);
	}
}
