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
