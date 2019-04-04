package com.blog.hysmyl.controller;

import com.blog.hysmyl.service.UserService;
import com.blog.hysmyl.utils.BlogLog;
import com.blog.hysmyl.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: Liu Mingyao
 * @create: 2018-11-06 11:56
 **/
@Controller
public class LoginController {
    @Autowired
    private BlogLog blogLog;

    @Autowired
    private UserService service;


    @PostMapping(value = "/user/index")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Map map, HttpSession session) {
        if (service.validateUser(username, password)) {
            session.setAttribute("user", username);
            return "redirect:/main.html";
        }
        map.put("msg", "失败");
        return "login";
    }

    @GetMapping("/registe/user")
    @ResponseBody
    public ResultMessage registe() {

        return ResultMessage.rightMsg();
    }
}
