package com.blog.hysmyl.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : LiuMingyao
 * @date : 2019/7/19 9:58
 * @description : TODO
 */
public class IpUtil {

    /**
     * @author  : LiuMing
     * @date : 2019/7/19 10:00
     * @description :   获取客户端ip地址
     */
    public static String getRealIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || ip.indexOf(":") > -1) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                ip = null;
            }
        }
        return ip;
    }
}