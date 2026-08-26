package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot task manager application.
 */
@SpringBootApplication
public class TaskmanagerApplication {

	/**
	 * Starts the embedded Spring Boot application.
	 *
	 * @param args command-line arguments supplied when the application starts
	 */
	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}

}
