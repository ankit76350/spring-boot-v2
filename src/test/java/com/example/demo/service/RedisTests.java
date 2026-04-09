package com.example.demo.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate; 


    @Disabled
    @Test
    void contextLoads() {
//        redisTemplate.opsForValue().set("email", "ankit@dsvcopr.com");
//        Object value =  redisTemplate.opsForValue().get("email");
        Object value1 =  redisTemplate.opsForValue().get("salary");
        int a = 10;
    }
}
