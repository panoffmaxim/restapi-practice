package com.epam.laboratory.restapipractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.epam.laboratory.restapipractice.repository")
public class RestapiPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiPracticeApplication.class, args);
	}

}
