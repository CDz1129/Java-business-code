package com.cdz.javacode.juc;

import cn.hutool.core.lang.func.VoidFunc0;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatchDemo1
 * <p>
 * 模拟并发
 *
 * @author CDz
 * @date 2021/4/28 10:18
 * @since 1.0.0
 */
public class CountDownLatchDemo1 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = () -> {
                System.out.println("NO" + no + "运动员准备就绪");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("NO" + no + "运动员起跑");
            };
            service.execute(runnable);
        }

        TimeUnit.MILLISECONDS.sleep(200);
        System.out.println("裁判检查");
        countDownLatch.countDown();
        service.shutdownNow();
    }
}
