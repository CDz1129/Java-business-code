package com.cdz.javacode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

@SpringBootApplication
@EnableJpaAuditing
public class JavacodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavacodeApplication.class, args);
    }

}
