package com.blog.hysmyl.utils.timer;

import com.blog.hysmyl.utils.BlogLog;
import com.blog.hysmyl.utils.Clear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Liu Mingyao
 * @Deprecated 定时任务工具类
 * @since 2018-11-30 19:00
 **/
public class TimerUtil {

    private Clear clear;

    public TimerUtil() {
        this.clear = new Clear();
    }

    private Date beginDate;

    /**
     * @return 返回定时任务的开启时间
     * @author Liu Ming
     */
    private Date getBeginDate() {
        //  格式为：yyyy-MM-dd HH:mm:ss
//        String beginDate = "2018-11-30 23:09:05";
        String beginDate = "2019-1-21 10:38:15";
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(beginDate);
        } catch (ParseException e) {
            new BlogLog().exceptionLog(getClass(), "传入时间格式有误,请输入yyyy-MM-dd HH:mm:ss格式!");
            e.printStackTrace();
        }
        return date == null ? null : date;
    }

    /**
     * @author Liu Ming
     * @deprecated 开启定时任务
     */
    public void startTimer() {
        Timer timer = new Timer();
        Calendar c = Calendar.getInstance();
        //设置清理任务在启动后的第二天凌晨执行
        c.set(Calendar.DATE, c.get(Calendar.DAY_OF_MONTH) + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        //定时清理图片
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                clear.clearUserUploadImg();
            }
        }, c.getTime(), 1000 * 60);
        //定时清理日志
        c.set(Calendar.DATE, c.get(Calendar.DAY_OF_MONTH) + 31);
//        System.out.println("定时任务启动成功         将在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime())+"清理日志文件!");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                clear.clearLog();
            }
        }, new Date(), 1000 * 60);
    }


}
