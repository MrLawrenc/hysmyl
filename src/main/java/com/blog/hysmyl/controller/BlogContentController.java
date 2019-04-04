package com.blog.hysmyl.controller;

import com.blog.hysmyl.pojo.BlogContent;
import com.blog.hysmyl.service.BlogContentService;
import com.blog.hysmyl.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Liu Mingyao
 * @since 2018-11-24 11:39
 **/
@Controller
public class BlogContentController {

    @Autowired
    private BlogContentService service;

    @PostMapping(value = "/user/publishBlog")
    @ResponseBody
    public ResultMessage publishBlog(@RequestBody BlogContent blogContent) {
        return service.pushBlog(blogContent);
    }
}
