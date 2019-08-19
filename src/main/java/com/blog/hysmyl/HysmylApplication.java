package com.blog.hysmyl;

import com.blog.hysmyl.utils.timer.TimerUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.blog.hysmyl.mapper")
public class HysmylApplication {


    public static void main(String[] args) {
        SpringApplication.run(HysmylApplication.class, args);

        new TimerUtil().startTimer();
    }
}
