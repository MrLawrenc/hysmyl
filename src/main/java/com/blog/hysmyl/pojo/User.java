package com.blog.hysmyl.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

/**
 * @author: Liu Mingyao
 * @create: 2018-11-06 12:03
 **/
@Alias("user")
@Getter
@Setter
@ToString
@Accessors(chain = true)//链式编程
public class User {
    private Integer id;
    private String username;
    private String password;
    private String hash;
}
