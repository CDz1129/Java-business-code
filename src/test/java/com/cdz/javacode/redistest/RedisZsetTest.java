package com.cdz.javacode.redistest;

import cn.hutool.core.util.RandomUtil;
import com.cdz.javacode.JavacodeApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * RedisZsetTest
 *
 * @author chendezhi
 * @date 2021/3/8 11:09
 * @since 1.0.0
 *
 * 测试使用redis Zset实现按时间排序
 */
@Component
public class RedisZsetTest extends JavacodeApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    private String zsetKey = "zset:test";
    @Test
    public void zsetAdd() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        for (int i = 0; i < 20; i++) {
            zSetOperations.add(zsetKey,RandomUtil.randomString(10),System.currentTimeMillis());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
