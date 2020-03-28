package com.cdz.javacode.juc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-03-25 00:05
 * <p>
 * 并发容器的错误使用
 * <p>
 * 复合错误使用
 **/
@Slf4j
@RestController
@RequestMapping("concurrentHashMap-test")
public class L01ConcurrentHashMap01 {

    //循环次数
    private static int LOOP_COUNT = 10000000;

    //线程数量
    private static int THREAD_COUNT = 10;

    //元素数量
    private static int ITEM_COUNT = 10;

    //错误用法
    private Map<String, Long> normaluse() throws InterruptedException {
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() ->
                IntStream.rangeClosed(1, LOOP_COUNT).parallel()
                        .forEach(i -> {
                            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                            synchronized (freqs){
                                if (freqs.containsKey(key)){
                                    freqs.put(key,freqs.get(key)+1);
                                }else {
                                    freqs.put(key,1L);
                                }
                            }
                        })
        );

        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return freqs;
    }

    public Map<String,Long> gooduse() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(()->IntStream.rangeClosed(1,LOOP_COUNT).parallel()
        .forEach(i->{
            String key = "item"+ ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            //computeIfAbsent 复合操作
            LongAdder longAdder = freqs.computeIfAbsent(key, k -> new LongAdder());
            longAdder.increment();
        }));

        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return freqs.entrySet().stream().collect(Collectors.toMap(
                e->e.getKey(),
                e->e.getValue().longValue()
        ));
    }

    @RequestMapping("good")
    public String good() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normaluse");
        Map<String, Long> normaluse = normaluse();
        stopWatch.stop();
        //校验数量
        Assert.isTrue(normaluse.size()==ITEM_COUNT,"normaluse size error");
        //校验累计总数
        Assert.isTrue(normaluse.entrySet().stream()
                .mapToLong(item -> item.getValue())
                .reduce(0L,Long::sum)==LOOP_COUNT,"normaluse count error");

        stopWatch.start("gooduse");
        Map<String, Long> gooduse = gooduse();
        stopWatch.stop();

        //检验数量
        Assert.isTrue(gooduse.size()==ITEM_COUNT,"gooduse size error");
        //校验累计总数
        Assert.isTrue(gooduse.entrySet().stream()
        .mapToLong(item->item.getValue())
        .reduce(0,Long::sum)==LOOP_COUNT,"gooduse count error");

        log.info(stopWatch.prettyPrint());
        return "ok";
    }



}
