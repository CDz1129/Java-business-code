package com.cdz.javacode.juc;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

@RestController
@RequestMapping("threadLocal-test")
public class L01ThreadLocal {

    private ThreadLocal<Integer> currUser = new ThreadLocal<>();

    @RequestMapping("wrong")
    public Map wrong(@RequestParam("userId")Integer userId){

        //用户之前获取信息
        String before = Thread.currentThread().getName()+":"+ currUser.get();

        //设置用户信息
        currUser.set(userId);

        //之后
        String after = Thread.currentThread().getName()+":"+ currUser.get();

        HashMap<Object, Object> map = new HashMap<>();

        map.put("before",before);
        map.put("after",after);
        return map;

        /**
         * test
         * 测试方便设置 Tomcat线程池大小为1
         * server.tomcat.max-threads=1
         *
         * 第一次
         * http://localhost:8080/threadLocal-test/wrong?userId=1
         * {
         * before: "http-nio-8080-exec-1:null",
         * after: "http-nio-8080-exec-1:0"
         * }
         *
         * 第二次
         * http://localhost:8080/threadLocal-test/wrong?userId=2
         * {
         * before: "http-nio-8080-exec-1:1",
         * after: "http-nio-8080-exec-1:2"
         * }
         *
         *
         * 原因：我们是将程序部署在Tomcat上的，而Tomcat多线程并发处理请求能力，使用线程池处理调用controller。
         */
    }


    @RequestMapping("right")
    public Map right(@RequestParam("userId")Integer userId){

        try {
            //用户之前获取信息
            String before = Thread.currentThread().getName()+":"+ currUser.get();

            //设置用户信息
            currUser.set(userId);

            //之后
            String after = Thread.currentThread().getName()+":"+ currUser.get();

            HashMap<Object, Object> map = new HashMap<>();

            map.put("before",before);
            map.put("after",after);
            return map;
        }finally {
            //声明式的删除，阿里手册也推荐声明式删除
            //不然会导致内存泄露
            currUser.remove();
        }
        /**
         * test
         *
         * http://localhost:8080/threadLocal-test/right?userId=1
         *{
         * before: "http-nio-8080-exec-1:null",
         * after: "http-nio-8080-exec-1:1"
         * }
         *
         * http://localhost:8080/threadLocal-test/right?userId=2
         * {
         * before: "http-nio-8080-exec-1:null",
         * after: "http-nio-8080-exec-1:2"
         * }
         */
    }





}
