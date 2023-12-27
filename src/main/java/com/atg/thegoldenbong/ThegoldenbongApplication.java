package com.atg.thegoldenbong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ThegoldenbongApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThegoldenbongApplication.class, args);
	}

}
