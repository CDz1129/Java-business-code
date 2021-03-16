package com.cdz.javacode.logging.duplicate;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoggingController
 *
 * @author chendezhi
 * @date 2021/3/8 15:48
 * @since 1.0.0
 */
@Log4j2
@RequestMapping("logging")
@RestController
public class LoggingController {

    @GetMapping("log")
    public void log(){

        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }


}
