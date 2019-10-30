package com.blog.hysmyl.utils;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Liu Mingyao
 * @description aop日志处理
 * @since 2018-11-09 15:30
 **/
@Aspect
@Component
public class BlogAspect {
    @Autowired
    private BlogLog blogLog;

    //    @Pointcut("execution(public * com.blog.hysmyl.service.impl..*.*(..))")配置切面
    @Pointcut("execution(public * com.blog.hysmyl.controller..*.*(..))")
    public void webLog() {
    }

    /**
     * @description 输出方法的所有参数
     */
    @Before("webLog()")
    public void Before(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder("进入");
        Class clz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        blogLog.appendStr(sb, methodName, "方法\t");
        String[] paramNames = blogLog.getParamNames(clz, methodName);
        Object[] paramValues = joinPoint.getArgs();
        if (paramValues == null) {
            sb.append("--该方法无参数");
            blogLog.exceptionLog(clz, sb.toString());
        } else {
            for (int i = 0; i < paramValues.length; i++) {
                Object obj = paramValues[i];
                if (obj instanceof ServletRequest || obj instanceof ServletResponse || obj instanceof HttpSession)
                    continue;
                String str = JSON.toJSONString(paramValues[i]);
                blogLog.appendStr(sb, "方法参数", paramNames[i], "的值是:", str, "\t");
            }
            blogLog.infoLog(clz, sb.toString());
        }
    }


    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
    }
}