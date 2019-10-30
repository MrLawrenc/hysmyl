package com.blog.hysmyl.controller;

import com.blog.hysmyl.api.LoginApi;
import com.blog.hysmyl.mapper.BlogContentMapper;
import com.blog.hysmyl.service.UserService;
import com.blog.hysmyl.utils.BlogLog;
import com.blog.hysmyl.utils.ResultMessage;
import com.blog.hysmyl.utils.kafka.KafKaCustomerProducer;
import com.blog.hysmyl.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: Liu Mingyao
 * @create: 2018-11-06 11:56
 **/
@Controller
public class LoginController implements LoginApi {
    @Autowired
    private BlogLog blogLog;

    @Autowired
    private UserService service;

    @Autowired
    private BlogContentMapper mapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private KafKaCustomerProducer producer;


    @PostMapping(value = "/user/index")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Map map, HttpServletRequest request) {
//        try {
//            int a=1/0;
//        }catch (Exception e){
//            new BlogLog().errorLog("我是测试{}--->{}",e,"刘明瑶","Hello");
//        }
//        new BlogLog().infoLog("我是测试的日志:{}",username);
        HttpSession session = request.getSession();
//        if (service.validateUser(username, password)) {
        if (true){
            session.setAttribute("user", username);

            //查询所有的blog列表，放入redis作为缓存
//            List<BlogContent> blogContents = mapper.getBlogList();
//            List<Object> list = new ArrayList<>(blogContents.size());
//            for (BlogContent blogContent : blogContents) {
//                list.add(blogContent);
//            }
//            redisUtil.ListSet("blogList", list);

            //使用kafka发送客户端相关信息（用户名 密码 登录ip地址 登录时间）
           /* String ip = IpUtil.getRealIpAddr(request);
            String kafkaMsg="{ip:"+ip+",username:"+username+",password:"+password+",createTime:"+new Date()+"}";
            producer.sendMessage("client_info", kafkaMsg);*/

            return "redirect:/main.html";
        }
        map.put("msg", "失败");
        return "login";
    }

    @GetMapping("/registe/user")
    @ResponseBody@Override
    public ResultMessage registe() {

        return ResultMessage.rightMsg();
    }
}
