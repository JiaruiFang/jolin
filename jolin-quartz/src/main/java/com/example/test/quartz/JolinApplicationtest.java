package com.example.test.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class JolinApplicationtest {

	public static void main(String[] args) {
		SpringApplication.run(JolinApplicationtest.class, args);
	}
	
}
