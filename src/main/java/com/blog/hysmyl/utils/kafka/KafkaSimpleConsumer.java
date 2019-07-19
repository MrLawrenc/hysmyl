package com.blog.hysmyl.utils.kafka;

import com.blog.hysmyl.utils.BlogLog;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BlogLog blogLog;

    // 简单消费者
    @KafkaListener(groupId = "kafka2", topics = "blogMsg")
    public void consumerBlog(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer) {
        System.out.println("博客消息:" + record.value() + "; topic:" + topic);
    }


    // 消费client_info消息，并存入数据库
    @KafkaListener(groupId = "kafka2", topics = "client_info")
    public void consumerClientInfo(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer) {
        //String kafkaMsg="{ip:"+ip+",username:"+username+",password:"+password+",createTime:"+new Date()+"}";
        String value = (String) record.value();
        blogLog.infoLog(getClass(), "接收到kafka发送的博客消息:" + value + "; topic:" + topic);

        String s1 = value.replace("{", "").replace("}", "");

//        Pattern pattern = Pattern.compile("ip.?");
//        Matcher matcher = pattern.matcher(s1);
//        if (matcher.find()) {
//            String group = matcher.group();
//        }

        String ip = s1.split(",")[0].split(":")[1];
        String username=s1.split(",")[1].split(":")[1];
        String password=s1.split(",")[2].split(":")[1];
        String createTime=s1.split(",")[3].split(":")[1];

        //存入数据库即可
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