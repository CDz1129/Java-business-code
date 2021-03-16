package com.cdz.javacode.logging.duplicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CommonMistakesApplication
 *
 * @author chendezhi
 * @date 2021/3/8 15:39
 * @since 1.0.0
 */
@SpringBootApplication
public class CommonMistakesApplication {
    public static void main(String[] args) {
        System.setProperty("logging.config", "classpath:com/cdz/javacode/logging/duplicate/loggerwrong.xml");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}
