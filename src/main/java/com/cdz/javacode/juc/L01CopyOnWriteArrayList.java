package com.cdz.javacode.juc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-03-28 16:08
 *
 * 有关copyOnWriteArrayList测试
 **/
@Slf4j
@RequestMapping("copyOnWriteArrayList-test")
@RestController
public class L01CopyOnWriteArrayList {

    /**
     * 测试并发写的性能
     *
     * copyOnWriteArrayList写能力极差
     * @return
     */
    @GetMapping("write")
    public Map testWrite(){
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        StopWatch stopWatch = new StopWatch();
        int loopCount = 100000;
        stopWatch.start("write：copyOnWriteArrayList");
        IntStream.rangeClosed(1,loopCount)
                .parallel()
                .forEach(__->copyOnWriteArrayList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();

        stopWatch.start("write：synchronizedList");
        IntStream.rangeClosed(1,loopCount).parallel()
                .forEach(__->synchronizedList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());

        Map<String, Integer> map = new HashMap<>();

        map.put("copyOnWriteArrayList",copyOnWriteArrayList.size());
        map.put("synchronizedList",synchronizedList.size());

        return map;
    }

    @GetMapping("read")
    public Map testRead(){
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        addAll(copyOnWriteArrayList);
        addAll(synchronizedList);

        StopWatch stopWatch = new StopWatch();
        int loopCount = 1000000;
        int count = copyOnWriteArrayList.size();
        stopWatch.start("Read:copyOnWriteArrayList");
        //copyOnWriteArrayList并发读
        IntStream.rangeClosed(1,loopCount).parallel()
        .forEach(__->copyOnWriteArrayList.get(ThreadLocalRandom.current().nextInt(count)));
        stopWatch.stop();

        stopWatch.start("Read:synchronizedList");
        //synchronizedList并发读
        IntStream.rangeClosed(1,loopCount).parallel()
                .forEach(__->synchronizedList.get(ThreadLocalRandom.current().nextInt(count)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

        Map map = new HashMap();
        map.put("copyOnWriteArrayList",copyOnWriteArrayList.size());
        map.put("synchronizedList",synchronizedList.size());

        return map;
    }

    private void addAll(List<Integer> list) {
        list.addAll(IntStream.rangeClosed(1,1000000).boxed().collect(Collectors.toList()));
    }

}
