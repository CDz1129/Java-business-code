package com.cdz.javacode.juc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-03-25 00:05
 **/
@Slf4j
@RestController
@RequestMapping("concurrentHashMap-test")
public class L01ConcurrentHashMap {

    private static int THREAD_COUNT = 10;

    private static int ITEM_COUNT = 1000;

    private ConcurrentHashMap<String, Long> getData(int count) {
        return LongStream.range(0, count)
                .boxed()
                .collect(Collectors.toConcurrentMap(
                        //key -> uuid
                        i -> UUID.randomUUID().toString(),
                        //value -> i
                        Function.identity(),
                        //重复key value如何运算
                        (o1, o2) -> o1,
                        ConcurrentHashMap::new
                ));
    }

    @RequestMapping("wrong")
    public String wrong() throws InterruptedException {
        //生成 900
        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        log.info("init size:{}",concurrentHashMap.size());

        //并行执行
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);

        forkJoinPool.execute(()-> IntStream.rangeClosed(1,10).parallel().forEach(i->{
            //还需要添加元素的个数
            int gap = ITEM_COUNT - concurrentHashMap.size();
            log.info("gap size:{}",gap);
            //补充元素
            concurrentHashMap.putAll(getData(gap));
        }));

        forkJoinPool.shutdown();

        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);

        //最后添加的元素个数会是1000个吗？
        log.info("map size:{}",concurrentHashMap.size());

        return "ok";
    }





}
