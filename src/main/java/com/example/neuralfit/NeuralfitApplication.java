package com.example.neuralfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NeuralfitApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeuralfitApplication.class, args);
    }
}
