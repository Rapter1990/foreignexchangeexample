package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class named {@link ForeignexchangeexampleApplication} to start the Role Permission example application.
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class ForeignexchangeexampleApplication {

	/**
	 * Main method to start the Spring Boot application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ForeignexchangeexampleApplication.class, args);
	}

}
