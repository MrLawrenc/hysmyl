package com.blog.hysmyl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author : LiuMingyao
 * @date : 2019/8/15 19:11
 * @description : 测试redis的分布式锁
 */
@RestController
public class TestController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${isClient}")
    private String isClient;


    /**
     * @author : LiuMing
     * @date : 2019/8/15 20:29
     * @description :   启动的配置更改端口和增加isClient属性，启动两个客户端一个服务端来进行测试，观察拿到锁的情况
     */
    @GetMapping("/liu")
    public void test() {


        /**
         * LocalDate toString之后格式:2019-08-15 。 LocalDateTime toString之后格式为:"2019-08-15T19:14:01.789461"
         * <p>data-->2019-08-15   time-->19:14:01.789461<p/>
         */
        LocalDateTime currentTime = LocalDateTime.now();


        new Thread(() -> {
            Boolean client = Boolean.valueOf(isClient);
            //清理文件缓存
            if (client) {
                boolean b = holdingLock("executeCleanFileDate", currentTime);
                if (b) {
                    System.out.println("客户端拿到锁，开始清理文件");
                    cleanFile();
                }

            } else {
                System.out.println("服务端开始清理文件");
                cleanFile();
            }

        }, "清理文件缓存线程").start();

        Thread.currentThread().setName("清理redis缓存线程");
        //清理Redis缓存
        boolean b = holdingLock("executeCleanRedisDate", currentTime);
        if (b) {
            System.out.println("本线程拿到锁，开始清理redis");
            cleanRedis();
        }

    }

    //当前线程是否持有锁
    private boolean holdingLock(String key, LocalDateTime currentTime) {
        //初始化redis锁，当前时间作为锁(redis单线程的，并且setIfAbsent方法在key存在的时候会返回false)
        Boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(key, currentTime.toString(), 6, TimeUnit.HOURS);
        if (isSuccess) {
            return true;
        }

        /**
         * 到这儿有两种可能:1.之前有人刚上锁。2.上一次定时任务的锁
         */

        Object current = redisTemplate.opsForValue().get(key);
        //如果锁是当天加的，则证明有其他进程在执行清缓存，直接退出。如果锁不是当天加的，则是之前加的锁，需要更新锁为今天。
        if (currentTime.toLocalDate().toString().equals(((String) redisTemplate.opsForValue().get(key)).substring(0, 10))) {
            System.out.println("没拿到" + key + "锁，直接结束");
            return false;
        } else {
            Object old = redisTemplate.opsForValue().getAndSet(key, currentTime.toString());
            if (current.equals(old)) {//保证了是 第一个 更改redis数据的进程/线程,虽然之后其他线程仍然会更改锁的值，但是已经不影响程序的执行了。
                System.out.println("拿到" + key + "锁");
                return true;
            }

            return false;
        }
    }

    private void cleanRedis() {
    }

    private void cleanFile() {
    }


}