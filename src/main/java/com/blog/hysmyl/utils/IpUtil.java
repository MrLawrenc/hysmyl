package com.blog.hysmyl.utils;

import com.blog.hysmyl.utils.log.LogUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author : LiuMingyao
 * @date : 2019/7/19 9:58
 * @description : TODO
 */
public class IpUtil {

    /**
     * 获取客户端ip地址
     */
    public static String getRealIpAddr(HttpServletRequest request) {

        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (StringUtils.isEmpty(ip) || ip.contains(":")) {
                ip = InetAddress.getLocalHost().getHostAddress();
            }
        } catch (Exception e) {
            LogUtil.errorLog("IPUtils ERROR ", ExceptionUtil.appendExceptionInfo(e));
        }
        return ip;
    }
}