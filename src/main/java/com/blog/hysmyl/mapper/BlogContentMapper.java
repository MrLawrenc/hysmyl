package com.blog.hysmyl.mapper;

import com.blog.hysmyl.pojo.BlogContent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Liu Mingyao
 * @Date 2018/11/24
 */
public interface BlogContentMapper {
    int add(BlogContent blogContent);

    List<BlogContent> getBlogList();

    BlogContent getBlog(Integer id);
}
