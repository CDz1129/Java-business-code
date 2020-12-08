package com.cdz.javacode.redistest;

import com.cdz.javacode.JavacodeApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RedisClientTestTest
 *
 * @author chendezhi
 * @date 2020/12/8 11:40
 * @since 1.0.0
 */
@Component
class RedisClientTestTest extends JavacodeApplicationTests {


    @Autowired
    private RedisClientTest redisClientTest;

    @Test
    void testLock() throws InterruptedException {
        new Thread(() -> redisClientTest.testLock()).start();
        new Thread(() -> redisClientTest.testLock()).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> redisClientTest.testLock()).start();
        TimeUnit.HOURS.sleep(1);
    }
}