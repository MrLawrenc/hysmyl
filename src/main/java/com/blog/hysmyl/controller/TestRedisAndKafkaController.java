package com.blog.hysmyl.controller;

import com.blog.hysmyl.utils.kafka.KafKaCustomerProducer;
import com.blog.hysmyl.utils.redis.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : LiuMingyao
 * @date : 2019/7/18 16:44
 * @description : kafka和redis测试类
 */
@RestController
public class TestRedisAndKafkaController {
    @Autowired
    private KafKaCustomerProducer producer;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/rk")
    public void testRedisAndKafka() {
        producer.sendMessage("kafka2", "我是测试kafka的消息");


        redisUtil.StringSet("liumy", "我是测试redis的消息");
        String liumy = redisUtil.StringGet("liumy");
        System.out.println(liumy);


        User u1=new User("11","22");
        User u2=new User("1111","2222");
        List<Object> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        redisUtil.ListSet("blogList",list);
        Object u = redisUtil.ListLeftPop("blogList");
        System.out.println("redis的列表操作:"+u);
    }
}

@AllArgsConstructor@ToString
class  User{
    String name;
    String age;
}