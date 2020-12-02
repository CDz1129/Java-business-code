package com.cdz.javacode.juc;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-11-11 07:51
 *
 * MySQL——Hikari 连接池
 * Redis——Jedis Poll
 * http——Apache HttpClient
 *
 * 连接超时时间 5S
 * 获取连接超时时间 10S 设置
 **/
public class L04ConnectPollProperties {

    public static void main(String[] args) {
        //Hikari 连接池
        //连接超时时间
        //spring.datasource.hikari.connection-timeout=10000
        //获取连接超时时间
        //spring.datasource.url=jdbc:mysql://localhost:6657/common_mistakes?connectTimeout=5000&characterEncoding=UTF-8&useSSL=false&rewriteBatchedStatements=true

        //lettuce
        //spring.redis.timeout=5000
        //spring.redis.lettuce.pool.max-wait=1000

        //Jedis Poll
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(10000);
        try (JedisPool jedisPool = new JedisPool(config, "localhost", 6379, 5000);
             Jedis jedis = jedisPool.getResource();
        ){
            jedis.set("test","test");
        }


        //Apache HttpClient
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(10000)
                .build();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:45678/twotimeoutconfig/test");
        httpGet.setConfig(requestConfig);
    }
}
