package com.blog.hysmyl.config;


import com.blog.hysmyl.component.MylocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Liu Mingyao
 * @since 2018-11-09 14:32
 * mvc配置类
 **/
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * @author Liu Ming
     * 添加映射关系 浏览器访问/index时候转到a.html视图
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/editor.html").setViewName("editor");
        //为了防止重复提交表单，登录成功之后重定向到main页面，再添加视图映射到index
        registry.addViewController("/main.html").setViewName("index");
    }

    /**
     * @author Liu Ming
     * 给容器添加国际化相关组件
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MylocaleResolver();
    }

    /**
     * @author Liu Ming
     * 登录拦截器
     */
 /*  @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //boot已经做好了静态资源js和css的排除，这里还需要排除图片等资源，因此直接排除static下的所有
       //但是访问的时候是url里面是不带/static的，所有不能使用/static/**的方式排除，使用/assets/**
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/login.html", "/", "/user/index","/assets/**","/registe/user");
    }*/

    /**
     * 添加上传图片的外部资源映射。上传图片到了项目根目录，springboot是无法访问到的
     *
     * @author Liu Ming
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/blog-img/**")
                .addResourceLocations("file:blog-img/");
        registry.addResourceHandler("/user-img/**")
                .addResourceLocations("file:user-img/");
    }
}
