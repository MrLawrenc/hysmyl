package com.blog.hysmyl.component;


import com.blog.hysmyl.utils.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author Liu Mingyao
 * @Deprecated 国际化
 * 可以在连接上带上区域信息
 * @since 2018-11-10 14:07
 **/
public class MylocaleResolver implements LocaleResolver {


    /**
     * @author Liu Ming
     * @deprecated 解析区域信息
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String parameter = request.getParameter("l");
        Locale locale = Locale.getDefault();
        if (StringUtils.isEmpty(parameter)) return locale;
        String[] params = parameter.split("_");
        locale = new Locale(params[0], params[1]);
        return locale;
    }


    @Override
    public void setLocale(HttpServletRequest httpServletRequest, @Nullable HttpServletResponse httpServletResponse, @Nullable Locale locale) {

    }
}
