package org.example.swe304;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.swe304.Model")
@EnableJpaRepositories(basePackages = "org.example.swe304.Repository")
public class Swe304Application {
    public static void main(String[] args) {
        SpringApplication.run(Swe304Application.class, args);
    }
}
