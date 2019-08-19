package com.blog.hysmyl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 封装日志记录的方法
 *
 * @author : Liu Mingyao
 * @create : 2018-11-07 13:39
 **/
@Component
public class BlogLog extends StringUtils {

    private Logger getLogger(Class clz) {
        return LoggerFactory.getLogger(clz);
    }

    private Logger getLogger(String clzName) {
        return LoggerFactory.getLogger(clzName);
    }

    /**
     * @author : LiuMing
     * @date : 2019/8/19 11:25
     * @description :   slf4j日志输出方法的封装；普通记录日志
     */
    public void infoLog(String str, Object... messages) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 3) return;
        String clzName = stackTrace[2].getClassName();
        getLogger(clzName).info(str, messages);
    }


    /**
     * @author : LiuMing
     * @date : 2019/8/19 13:37
     * @description :   错误日志
     */
    public void errorLog(String str, Object... messages) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 3) return;
        String clzName = stackTrace[2].getClassName();
        getLogger(clzName).error(str, messages);
    }

    /**
     * @author : LiuMing
     * @date : 2019/8/19 13:42
     * @description :   带有异常信息的错误日志
     */
    public void errorLog(String str, Throwable t, Object... messages) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 3) return;
        String clzName = stackTrace[2].getClassName();
        getLogger(clzName).error(str + " 异常信息如下:\n" + ExceptionUtil.getExceptionInfo(t), messages);
    }

    /**
     * @author : LiuMing
     * @date : 2019/8/19 13:53
     * @description :   带有异常信息的警告日志
     */
    public void warnLog(String str, Throwable t, Object... messages) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 3) return;
        String clzName = stackTrace[2].getClassName();
        getLogger(clzName).warn(str + " 异常信息如下:\n" + ExceptionUtil.getExceptionInfo(t), messages);
    }

    /**
     * @author : LiuMing
     * @date : 2019/8/19 13:53
     * @description :   警告日志
     */
    public void warnLog(String str, Object... messages) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 3) return;
        String clzName = stackTrace[2].getClassName();
        getLogger(clzName).warn(str, messages);
    }

    /**
     * @param messages 需要拼接的参数
     * @author Liu Ming
     * 记录普通的日志信息
     */
    public void infoLog(Class clz, Object... messages) {
        StringBuilder sb = new StringBuilder("");
        StringUtils.appendStr(sb, messages);
        getLogger(clz).info(sb.toString());
        sb = null;
    }

    /**
     * @author : Liu Ming
     * 记录异常日志信息
     */
    public void exceptionLog(Class clz, Object... exeMessage) {
        StringBuilder sb = new StringBuilder("");
        StringUtils.appendStr(sb, exeMessage);
        getLogger(clz).warn(sb.toString());
        sb = null;
    }


    /**
     * @param clz        方法所在的类
     * @param methodName 方法名称
     * @author Liu Ming
     * 获取方法所有参数的名称
     */
    String[] getParamNames(Class clz, String methodName) {
        Parameter[] parameters;
        String paramNames[] = new String[10];
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                parameters = method.getParameters();
                if (parameters.length == 0) return null;//注意无参数情况下长度为0
                //j8新增获取方法参数名称的方法
                LocalVariableTableParameterNameDiscoverer pnd = new LocalVariableTableParameterNameDiscoverer();
                paramNames = pnd.getParameterNames(method);
            }
        }
        return paramNames;
    }

}
