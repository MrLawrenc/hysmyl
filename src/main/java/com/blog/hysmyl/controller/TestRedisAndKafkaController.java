package com.blog.hysmyl.controller;

import com.blog.hysmyl.utils.kafka.KafKaCustomrProducer;
import com.blog.hysmyl.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : LiuMingyao
 * @date : 2019/7/18 16:44
 * @description : kafka和redis测试类
 */
@RestController
public class TestRedisAndKafkaController {
    @Autowired
    private KafKaCustomrProducer producer;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/rk")
    public void testRedisAndKafka() {
        producer.sendMessage("blogMsg", "我是测试kafka的消息");


        redisUtil.StringSet("liumy", "我是测试redis的消息");
        String liumy = redisUtil.StringGet("liumy");
        System.out.println(liumy);
    }
}