package com.cdz.javacode.math;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Montecarlo
 *
 * @author CDz
 * @date 2021/5/21 10:14
 * @since 1.0.0
 * 蒙特卡洛法 实践
 */
@Slf4j
public class Montecarlo {

    public static void main(String[] args) {
        pi();
        birthday();
    }

    /**
     * 测算 生日悖论
     */
    private static void birthday() {

        int numPeople = 23;
        int trials = 1_000_000;
        int success = 0;
        for (int i = 0; i < trials; i++) {
            int[] bday = genBdayList(numPeople);
            int[] uniques = uniqueSlice(bday);
            if (uniques.length != numPeople) {
                success++;
            }
        }
        double probability = success / (double)trials;
        log.info("人数：{}，至少有两个人同岁生日,的概率是：{}%",numPeople,probability);
        /**
         * 11:03:58.370 [main] INFO com.cdz.javacode.math.Montecarlo - 人数：26，至少有两个人同岁生日,的概率是：0.606725%
         * 11:17:48.127 [main] INFO com.cdz.javacode.math.Montecarlo - 人数：23，至少有两个人同岁生日,的概率是：0.516425%
         *
         * https://en.wikipedia.org/wiki/Birthday_problem
         */
    }

    //简易去重
    private static int[] uniqueSlice(int[] bday) {
        return Arrays.stream(bday).distinct().toArray();
    }

    private static int[] genBdayList(int numPeople) {
        return IntStream.range(0,numPeople).map(e->RandomUtils.nextInt(356)).toArray();
    }

    /**
     * 测算PI
     */
    private static void pi() {
        Random random = new Random();
        int print = 1_000_000;
        int sucess = 0;
        for (int i = 0; i < print; i++) {
            float x = 2 * random.nextFloat() - 1;
            float y = 2 * random.nextFloat() - 1;
            if (x * x + y * y < 1) {
                sucess++;
            }
        }
        double piApprox = 4.0 * (sucess / (float) print);
        double pi = Math.PI;
        double errorPc = 100 * (Math.abs(piApprox - pi)) / pi;
        log.info("蒙特卡洛法计算PI：" + piApprox);
        log.info("实际PI：" + pi);
        log.info("误差率：" + errorPc + "%");
        /**
         * 11:03:55.108 [main] INFO com.cdz.javacode.math.Montecarlo - 蒙特卡洛法计算PI：3.1420159339904785
         * 11:03:55.111 [main] INFO com.cdz.javacode.math.Montecarlo - 实际PI：3.141592653589793
         * 11:03:55.111 [main] INFO com.cdz.javacode.math.Montecarlo - 误差率：0.013473433616599887%
         */
    }
}
