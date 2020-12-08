package com.cdz.javacode.redistest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-05-19 22:19
 * <p>
 * 问题：
 * 既然Redis是单线程串行的，那么客户端为什么使用Redis连接池？
 * 使用Redis连接池与全局Redis连接到底有那些不同？
 **/
@RequestMapping("redis")
@RestController
@Slf4j
public class RedisClientTest {

    static Jedis jedis = new Jedis("localhost", 6379);

    static JedisPool jedisPool = new JedisPool();

    private static final String lock = "lock";

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean lock() {
        log.info(Thread.currentThread().getName()+" lock被执行");
        return redisTemplate.opsForValue().setIfAbsent(lock, 1, 3, TimeUnit.MINUTES);
    }

    public void unLock() {
        log.info(Thread.currentThread().getName()+" unLock被执行");
        redisTemplate.delete(lock);
    }


    /**
     * 加锁与解锁
     *
     * 一点不能把加锁逻辑写在 try中，这样会导致，
     * finally的解锁业务不论上锁成功或失败都会解锁
     */
    @GetMapping("test-lock")
    public void testLock() {
        if (!lock()) {
            log.info(Thread.currentThread().getName()+" 拒绝执行");
            return;
        }


        try {
            TimeUnit.SECONDS.sleep(5);
            log.info(Thread.currentThread().getName()+" 加锁执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            unLock();
        }
    }

    public static void main(String[] args) {

        String key = "key";
        StopWatch stopWatch = new StopWatch();



//        stopWatch.start("单连接 创造数据");
        //创造数据
        for (int i = 0; i < 100; i++) {
            String set = jedis.set(key, 1+"");
        }
//        stopWatch.stop();

        stopWatch.start("单个连接读取数据");
        //单个连接读取数据
        for (int i = 0; i < 100; i++) {
            Long incr = jedis.incr(key);
        }
        stopWatch.stop();

        ExecutorService executorService = Executors.newFixedThreadPool(10);


        //初始化 线程池所有的线程 模拟多个线程调用
        for (int i = 0; i < 10; i++) {
            executorService.submit(()->{
                Jedis resource = jedisPool.getResource();
                try {

                    String s = jedis.get(key);
                } finally {
                    resource.close();
                }
            });
        }
        while (executorService.isTerminated());


        CountDownLatch countDownLatch = new CountDownLatch(100);
        stopWatch.start("单个连接 多线程读取数据");
        //单个连接读取数据
        for (int i = 0; i < 100; i++) {
            int a= i;
            executorService.submit(()->{
                Long incr = jedis.incr(key);
                System.out.println("单连接多线程："+incr);
//                countDownLatch.countDown();
            });
        }

        while (executorService.isTerminated());
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        stopWatch.stop();


        stopWatch.start("poll 读取数据");
        //poll 读取数据
        for (int i = 0; i < 100; i++) {
            executorService.submit(()->{
                Jedis resource = jedisPool.getResource();
                try {
                    Long incr = jedis.incr(key);
                    System.out.println("连接池多线程："+incr);
                } finally {
                    resource.close();
                }
            });
        }
        while (executorService.isTerminated());
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
        executorService.shutdown();
    }

}
