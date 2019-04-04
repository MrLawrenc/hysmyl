package com.blog.hysmyl.service.impl;

import com.blog.hysmyl.mapper.UserMapper;
import com.blog.hysmyl.pojo.User;
import com.blog.hysmyl.service.UserService;
import com.blog.hysmyl.utils.BlogLog;
import com.blog.hysmyl.utils.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户相关业务
 *
 * @author Liu Mingyao
 * @since 2018-11-09 15:36
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private BlogLog blogLog;

    @Override
    public List<User> list(String a, String s) {
        System.out.println("调用UserServiceImpl" + a + s);
        return null;
    }

    /**
     * @author Liu Ming
     * @deprecated 校验用户账号密码是否正确
     */
    @Override
    public boolean validateUser(String username, String password) {
        User user = mapper.getUserByName(username);
        try {
            String hash = user.getHash();
            boolean b = PasswordHash.validatePassword(password, hash);
            if (b) return true;
        } catch (Exception e) {
            blogLog.exceptionLog(getClass(), e.getStackTrace());
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean cancelUser(User user) {
        return false;
    }
}
