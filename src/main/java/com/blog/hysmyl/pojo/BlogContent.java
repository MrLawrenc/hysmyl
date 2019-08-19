package com.blog.hysmyl.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author Liu Mingyao
 * @since 2018-11-25 08:55
 * 博客内容实体类
 **/
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Alias("blogContent")
public class BlogContent {
    private Integer id;
    private String title;
    private Date createTime;
    private Date updateTime;
    private String content;
}
