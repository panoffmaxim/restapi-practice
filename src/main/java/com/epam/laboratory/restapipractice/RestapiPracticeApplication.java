package com.epam.laboratory.restapipractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class RestapiPracticeApplication {
    public static void main(String[] args) {

        SpringApplication.run(RestapiPracticeApplication.class, args);
    }
}
