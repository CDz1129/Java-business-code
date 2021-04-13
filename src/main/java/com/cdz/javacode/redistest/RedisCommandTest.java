package com.cdz.javacode.redistest;

import com.google.common.collect.Lists;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.cluster.api.async.RedisClusterAsyncCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * RedisPiplineComment
 *
 * @author chendezhi
 * @date 2021/4/13 13:43
 * @since 1.0.0
 */
@RestController
@RequestMapping("redis/command")
@Slf4j
public class RedisCommandTest {
    @Autowired
    private RedisTemplate redisTemplate;
    static Jedis jedis = new Jedis("localhost", 6379);

    private String testKey = "testkey2";
    @GetMapping("pipelined")
    public void testPipline(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        StopWatch stopWatch = new StopWatch();
        // -------------- redisTemplate::executePipelined(redisTemplate默认是Lettuce)----------------
        stopWatch.start("redisTemplate pipeline");
        List list = redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            redisConnection.openPipeline();
            for (int i = 0; i < 100; i++) {
                redisConnection.incr(testKey.getBytes(StandardCharsets.UTF_8));
            }
            redisConnection.closePipeline();
            return null;
        });
        stopWatch.stop();
        log.info("redisTemplate res:{}",valueOperations.get(testKey));

        // -------------- 使用LettuceConnection ----------------
        stopWatch.start("Lettuce real pip");
        LettuceConnection connection = (LettuceConnection)redisTemplate.getConnectionFactory().getConnection();
        RedisClusterAsyncCommands<byte[], byte[]> commands = connection.getNativeConnection();
        commands.setAutoFlushCommands(false);
        commands.setTimeout(Duration.ofMinutes(10));
        List<RedisFuture<Long>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            futures.add(commands.incr(testKey.getBytes(StandardCharsets.UTF_8)));
        }
        commands.flushCommands();
        List<Object> result = Lists.newArrayList();
        futures.forEach(e -> {
            try {
                result.add(e.get());
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        });

        //注意这里需要重新设置回去
        commands.setAutoFlushCommands(true);
        connection.close();
        stopWatch.stop();
        log.info("Lettuce real pip res:{}",valueOperations.get(testKey));

        // -------------- 使用jedis Connection ----------------
        stopWatch.start("jedis pipeline");
        Pipeline pipelined = jedis.pipelined();
        for (int i = 0; i < 100; i++) {
            pipelined.incr(testKey.getBytes(StandardCharsets.UTF_8));
        }
        List<Object> objects = pipelined.syncAndReturnAll();
        stopWatch.stop();
        log.info("jedis res:{}",valueOperations.get(testKey));

        //  -------------- 进阶使用redisTemplate 只设置commands.setAutoFlushCommands(false); ----------------
        stopWatch.start("redisTemplate pipeline 二次尝试");
        RedisConnection connection1 = redisTemplate.getConnectionFactory().getConnection();
        connection1.openPipeline();
        for (int i = 0; i < 100; i++) {
            connection1.incr(testKey.getBytes(StandardCharsets.UTF_8));
        }
        List<Object> objects1 = connection1.closePipeline();
        stopWatch.stop();
        log.info("redisTemplate pipeline 二次尝试:{}",valueOperations.get(testKey));


        stopWatch.start("redisTemplate pipeline 三次尝试");
        List list3 = redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            redisConnection.openPipeline();
            for (int i = 0; i < 100; i++) {
                redisConnection.incr(testKey.getBytes(StandardCharsets.UTF_8));
            }
            redisConnection.closePipeline();
            return null;
        });
        stopWatch.stop();
        log.info("redisTemplate pipeline 三次尝试:{}",valueOperations.get(testKey));
        log.info("对比结果：{}",stopWatch.prettyPrint());

    }

    /**
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 699023700  089%  redisTemplate pipeline
     * 019157100  002%  Lettuce real pip
     * 027347200  003%  jedis pipeline
     * 018890000  002%  redisTemplate pipeline 二次尝试
     * 019090300  002%  redisTemplate pipeline 三次尝试
     */
}
