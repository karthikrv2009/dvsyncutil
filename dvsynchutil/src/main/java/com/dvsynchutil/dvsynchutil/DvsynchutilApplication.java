package com.dvsynchutil.dvsynchutil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories("com.dvsynchutil.dvsynchutil.repository")
@EntityScan("com.dvsynchutil.dvsynchutil.entity")
public class DvsynchutilApplication {

    public static void main(String[] args) {
        SpringApplication.run(DvsynchutilApplication.class, args);
    }
}
