package com.blog.hysmyl.controller;

import com.blog.hysmyl.VO.BlogContentVO;
import com.blog.hysmyl.pojo.BlogContent;
import com.blog.hysmyl.service.BlogContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Liu Mingyao
 * @since 2018-11-12 13:50
 **/
@Controller
public class IndexController {

    @Autowired
    private BlogContentService blogContentService;

    // 个人信息
    @GetMapping("/user/about")
    public String about() {
        return "about";
    }

    //联系我们
    @GetMapping("/user/contact")
    public String contact() {
        return "contact";
    }

    //写博客
    @GetMapping("/user/record")
    public String record() {
        return "record";
    }

    //自己所有的博客列表
    @GetMapping("/blog/list")
    public String blogList(Model model) {

        List<BlogContentVO> blogContentVOS = blogContentService.getBlogList();
        model.addAttribute("blogContentVOS", blogContentVOS);
        return "blogList";
    }

    //查看具体某一篇博客
    @GetMapping("/getBlog")
    public String getbBlog(Integer id, Model model) {
        System.out.println("需要查看id为:" + id + "的博客");
        BlogContent blog = blogContentService.getBlog(id);
        model.addAttribute("blog", blog);
        return "blog";
    }

    @PostMapping("/user/feedback")
    public String feedback(String name, String email, String subject, String message) {
        return "index";
    }
}
