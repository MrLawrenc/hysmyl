package com.blog.hysmyl;

import ch.qos.logback.core.FileAppender;
import com.blog.hysmyl.utils.timer.TimerUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

@SpringBootApplication
@MapperScan("com.blog.hysmyl.mapper")
public class HysmylApplication {

   static {
   }
    public static void main(String[] args) {
        SpringApplication.run(HysmylApplication.class, args);

        new TimerUtil().startTimer();
    }
}
