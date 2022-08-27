package com.dayalbagh.snc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;



@SpringBootApplication
public class SncApplication {

	public static void main(String[] args) {
		SpringApplication.run(SncApplication.class, args);
	}

	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		
		return builder.sources(SncApplication.class);
	
	}
}
