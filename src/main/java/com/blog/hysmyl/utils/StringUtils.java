package com.blog.hysmyl.utils;

import java.util.Arrays;

/**
 * @author : Liu Mingyao
 * @since : 2018-11-07 14:16
 **/
public class StringUtils {

    /**
     * @ Author: Liu Ming
     * @deprecated :  在sb之后追加拼接objects里面所有的字符串
     */
    public static void appendStr(StringBuilder sb, Object... objects) {
        Arrays.asList(objects).forEach(o -> sb.append(o));
    }

    /**
     * @author Liu Ming
     * @deprecated 判断传入的字符串是否为empty
     */
    public static boolean isEmpty(String str) {
        return org.springframework.util.StringUtils.isEmpty(str);
    }
}
