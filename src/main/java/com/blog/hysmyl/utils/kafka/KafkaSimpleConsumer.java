package com.blog.hysmyl.utils.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author : LiuMingyao
 * @date : 2019/7/18 16:36
 * @description : kafka简单消费工具类
 */
@Component
public class KafkaSimpleConsumer {


    // 简单消费者
    @KafkaListener(groupId = "kafka2", topics = "blogMsg")
    public void consumerBlog(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer) {
        System.out.println("博客消息:" + record.value() + "; topic:" + topic);
        /*
         * 如果需要手工提交异步 consumer.commitSync();
         * 手工同步提交 consumer.commitAsync()
         */
    }


//    // 简单消费者
//    @KafkaListener(groupId = "kafka2", topics = "需要监听的topic主题")
//    public void consumer1_1(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer) {
//        System.out.println("消费者收到消息:" + record.value() + "; topic:" + topic);
//        /*
//         * 如果需要手工提交异步 consumer.commitSync();
//         * 手工同步提交 consumer.commitAsync()
//         */
//    }
}