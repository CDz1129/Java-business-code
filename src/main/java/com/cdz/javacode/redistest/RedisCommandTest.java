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
            for (int i = 0; i < 100; i++) {
                redisConnection.incr(testKey.getBytes(StandardCharsets.UTF_8));
            }
            return null;
        });
        stopWatch.stop();
        log.info("redisTemplate res:{}",valueOperations.get(testKey));


        // -------------- redisTemplate::executePipelined(redisTemplate默认是Lettuce)----------------
        stopWatch.start("redisTemplate execute not executePipelined");
        Object execute = redisTemplate.execute((RedisCallback<Object>) redisConnection -> {
            for (int i = 0; i < 100; i++) {
                redisConnection.incr(testKey.getBytes(StandardCharsets.UTF_8));
            }
            return null;
        });
        stopWatch.stop();
        log.info("redisTemplate execute res:{}",valueOperations.get(testKey));

        // -------------- 使用jedis Connection ----------------
        stopWatch.start("jedis pipeline");
        Pipeline pipelined = jedis.pipelined();
        for (int i = 0; i < 100; i++) {
            pipelined.incr(testKey.getBytes(StandardCharsets.UTF_8));
        }
        List<Object> objects = pipelined.syncAndReturnAll();
        stopWatch.stop();
        log.info("jedis res:{}",valueOperations.get(testKey));

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
            for (int i = 0; i < 100; i++) {
                redisConnection.incr(testKey.getBytes(StandardCharsets.UTF_8));
            }
            return null;
        });
        stopWatch.stop();
        log.info("redisTemplate pipeline 三次尝试:{}",valueOperations.get(testKey));
        log.info("对比结果：{}",stopWatch.prettyPrint());

    }

    /**
     * 第一次：
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 615129200  075%  redisTemplate pipeline  //可以看到第一个建立连接需要耗时比较长
     * 137835000  017%  redisTemplate execute not executePipelined
     * 025660500  003%  jedis pipeline
     * 013333900  002%  Lettuce real pip
     * 014415200  002%  redisTemplate pipeline 二次尝试
     * 009258200  001%  redisTemplate pipeline 三次尝试
     *
     * 第二次：
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 028989500  009%  redisTemplate pipeline
     * 286694100  085%  redisTemplate execute not executePipelined
     * 001764500  001%  jedis pipeline
     * 006789700  002%  Lettuce real pip
     * 007459100  002%  redisTemplate pipeline 二次尝试
     * 006603600  002%  redisTemplate pipeline 三次尝试
     *
     * 第三次：
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 009823200  005%  redisTemplate pipeline
     * 172719600  087%  redisTemplate execute not executePipelined
     * 002304900  001%  jedis pipeline
     * 004799700  002%  Lettuce real pip
     * 004264400  002%  redisTemplate pipeline 二次尝试
     * 005717000  003%  redisTemplate pipeline 三次尝试
     */
}
