package com.cdz.javacode.juc;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * L03ThreadPool
 *
 * @author chendezhi
 * @date 2020/12/1 15:30
 * @since 1.0.0
 */
@RestController
@RequestMapping("03ThreadPool")
@Slf4j
public class L03ThreadPool {

    @GetMapping("oom1")
    public void oom1() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        printStats(executorService);
        for (int i = 0; i < 100000000; i++) {
            executorService.execute(() -> {
                String payload = IntStream.rangeClosed(1, 1000000)
                        .mapToObj(__ -> "a")
                        .collect(Collectors.joining("")) + UUID.randomUUID().toString();
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info(payload);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }


    @GetMapping("right")
    public Integer right() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                2, 5,
                5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        printStats(poolExecutor);
        IntStream.rangeClosed(1, 20).forEach(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int id = atomicInteger.incrementAndGet();
                    try {
                        poolExecutor.submit(() -> {
                            log.info("{} started", id);
                            try {
                                TimeUnit.SECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            log.info("{} finished", id);
                        });
                    } catch (Exception e) {
                        log.error("error submit task {}", id, e);
                        atomicInteger.decrementAndGet();
                    }
                }
        );
        TimeUnit.SECONDS.sleep(60);
        return atomicInteger.get();
    }


    private void printStats(ThreadPoolExecutor threadPool) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("=========================");
            log.info("pool size : {}", threadPool.getPoolSize());
            log.info("active pool size : {}", threadPool.getActiveCount());
            log.info("number of tasks completed : {}", threadPool.getCompletedTaskCount());
            log.info("number of tasks in queue : {}", threadPool.getQueue().size());
            log.info("=========================");
        }, 0, 1, TimeUnit.SECONDS);
    }


}
