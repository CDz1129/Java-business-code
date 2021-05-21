package com.cdz.javacode.math;

import org.apache.commons.lang.math.RandomUtils;

import java.util.Random;

/**
 * Montecarlo
 *
 * @author CDz
 * @date 2021/5/21 10:14
 * @since 1.0.0
 * 蒙特卡洛法 实践
 */
public class Montecarlo {

    public static void main(String[] args) {
        pi();
    }

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
        double piApprox = 4.0*(sucess/(float)print);
        double pi = Math.PI;
        double errorPc = 100*(Math.abs(piApprox-pi))/pi;
        System.out.println("蒙特卡洛法计算PI："+piApprox);
        System.out.println("实际PI："+pi);
        System.out.println("误差率："+errorPc+"%");
    }
}
