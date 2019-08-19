package com.blog.hysmyl.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Liu Mingyao
 * @Deprecated 封装响应给前台的数据
 * @since 2018-11-10 21:09
 **/
@AllArgsConstructor
@Getter
public class ResultMessage {
    private boolean success;
    private int errno;//如果有错误，errno != 0，可通过下文中的监听函数 fail 拿到该错误码进行自定义处理
    private Object data;
    private String msg;


    public static ResultMessage rightMsg(Object data) {
        return new ResultMessage(true, 0, data, "");
    }

    public static ResultMessage wrongMsg(Object data) {
        return new ResultMessage(false, 500, data, "");
    }

    public static ResultMessage rightMsg(Object data, String msg) {
        return new ResultMessage(true, 0, data, msg);
    }

    public static ResultMessage wrongMsg(Object data, String msg) {
        return new ResultMessage(false, 500, data, msg);
    }

    public static ResultMessage rightMsg(String msg) {
        return new ResultMessage(true, 0, null, msg);
    }

    public static ResultMessage wrongMsg(String msg) {
        return new ResultMessage(false, 500, null, msg);
    }

    public static ResultMessage rightMsg() {
        return new ResultMessage(true, 0, null, "");
    }

    public static ResultMessage wrongMsg() {
        return new ResultMessage(false, 500, null, "");
    }
}
