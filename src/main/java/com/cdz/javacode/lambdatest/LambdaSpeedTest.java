package com.cdz.javacode.lambdatest;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-04-20 08:09
 **/
public class LambdaSpeedTest {
    public static void main(String[] args) {


        ArrayList<Integer> list = new ArrayList<>();

        Random random = new Random(100000);
        for (int i = 0; i < 1000000; i++) {
            list.add(random.nextInt());
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("stream test");
        Optional<Integer> reduce = list.stream().reduce(Integer::sum);
        System.out.println(reduce.get());
        stopWatch.stop();

        stopWatch.start("for test");
        int count =0;
        for (Integer integer : list) {
            count += integer;
        }
        System.out.println(count);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }
}
