package com.personal.project.angi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class AnGiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnGiApplication.class, args);
    }

}
