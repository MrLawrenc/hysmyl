package com.blog.hysmyl.service;

import com.blog.hysmyl.pojo.User;

import java.util.List;

/**
 * @author Liu Mingyao
 * @date 2018/11/9
 */
public interface UserService {
    List<User> list(String a, String s);

    /**
     * @author Liu Ming
     * @description 校验用户是否登录
     */
    boolean validateUser(String username, String password);

    /**
     * @author Liu Ming
     * @description 添加用户
     */
    boolean addUser(User user);

    /**
     * @author Liu Ming
     * @description 注销用户
     */
    boolean cancelUser(User user);
}
