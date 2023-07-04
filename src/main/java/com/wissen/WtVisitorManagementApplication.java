package com.wissen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WtVisitorManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(WtVisitorManagementApplication.class, args);
	}

}
