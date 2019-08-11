package com.blog.hysmyl.mapper;

import com.blog.hysmyl.pojo.User;

import java.util.List;

/**
 * @Author Liu Mingyao
 * @Date 2018/11/6
 */

public interface UserMapper {
    List<User> list();

    User getUserByName(String username);

}
