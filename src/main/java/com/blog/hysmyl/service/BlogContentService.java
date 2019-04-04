package com.blog.hysmyl.service;

import com.blog.hysmyl.VO.BlogContentVO;
import com.blog.hysmyl.pojo.BlogContent;
import com.blog.hysmyl.utils.ResultMessage;

import java.util.List;

/**
 * @author Liu Mingyao
 * @since 2018-11-24 10:59
 **/
public interface BlogContentService {

    ResultMessage pushBlog(BlogContent blogContent);

    List<BlogContentVO> getBlogList();

    BlogContent getBlog(Integer id);
}
