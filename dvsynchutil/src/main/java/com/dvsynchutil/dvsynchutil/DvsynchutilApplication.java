package com.dvsynchutil.dvsynchutil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EntityScan("com.dvsynchutil.dvsynchutil.entity")  // Adjust the package path to your actual entity location
@EnableJpaRepositories("com.dvsynchutil.dvsynchutil.repository")

@SpringBootApplication
public class DvsynchutilApplication {

    public static void main(String[] args) {
        SpringApplication.run(DvsynchutilApplication.class, args);
    }
}
