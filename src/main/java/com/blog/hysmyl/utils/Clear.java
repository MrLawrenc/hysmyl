package com.blog.hysmyl.utils;

import java.io.File;
import java.io.IOException;

/**
 * 清理服务器中的垃圾文件
 *
 * @author Liu Mingyao
 * @since 2019-03-15 14:11
 **/
public class Clear {

    private BlogLog blogLog;

    public Clear() {
        this.blogLog = new BlogLog();
    }

    /**
     * 定时晚上12点清理用户在编辑博客上传之后又删除的无用图片  在target/classes/static/user-img 文件夹下
     *
     * @param
     * @author Liu Ming
     */
    public void clearUserUploadImg() {
        blogLog.infoLog(getClass(), "马上清理user-img文件夹!");
        String path = getClass().getResource("/").toString().substring(5) + "static/user-img";
        File deleteDir = new File(path);
        for (File file : deleteDir.listFiles()) {
            file.delete();
        }
    }

    /**
     * 每隔清理日志文件,只保留最近30天的日志
     *
     * @author Liu Ming
     */
    public void clearLog() {
        blogLog.infoLog(getClass(), "马上清理日志文件!");
        String s = "";
        try {
            s = new File(".").getCanonicalPath() + "/log";
        } catch (IOException e) {
            blogLog.exceptionLog(getClass(), "定时任务-清理日志文件:获取日志文件路径异常!");
            e.printStackTrace();
        }
        File[] files = new File(s).listFiles();
        for (int j = 0; j < files.length - 30; j++) {
            files[j].delete();
        }
    }
}
