package com.cdz.javacode.redistest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * RedisZsetTest
 *
 * @author chendezhi
 * @date 2021/3/8 11:32
 * @since 1.0.0
 */
@RestController
@RequestMapping("redis-zset")
public class RedisZsetTest {
    @Autowired
    private RedisTemplate redisTemplate;

    private String zsetKey = "zset:test";

    @GetMapping("add-data")
    public void addData(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        for (int i = 0; i < 20; i++) {
            zSetOperations.add(zsetKey, RandomUtil.randomString(10),System.currentTimeMillis());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @GetMapping("get-data")
    public List<Map<Object, Object>> getData(){
        ZSetOperations<String,String> zSetOperations = redisTemplate.<String,String>opsForZSet();
//        Set<String> range = zSetOperations.range(zsetKey, 0, -1);
        Set<ZSetOperations.TypedTuple<String>> typedTuples = zSetOperations.reverseRangeWithScores(zsetKey, 0, -1);
        List<Map<Object, Object>> set = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> typedTuple : typedTuples) {
            String value = typedTuple.getValue();
            Double score = typedTuple.getScore();
            Map<Object, Object> map =
                    MapUtil.builder().put(value, DateUtil.date(score.longValue())).build();
            set.add(map);
        }
        return set;
    }
    @GetMapping("del-data")
    public void delData(){
        BoundZSetOperations boundZSetOperations = redisTemplate.boundZSetOps(zsetKey);
        Long size = boundZSetOperations.size();
        boundZSetOperations.removeRange(0,size/2);
    }
}
