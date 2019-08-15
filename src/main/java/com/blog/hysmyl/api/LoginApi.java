package com.blog.hysmyl.api;

import com.blog.hysmyl.utils.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "登录接口", description = "LoginController | 登录相关接口")
public interface LoginApi {

    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String")})
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Map map, HttpServletRequest request) ;

    @ApiOperation(value = "获图书细信息", notes = "根据url的id来获取详细信息")
//    @ApiImplicitParam(name = "id", value = "ID", required = false, dataType = "Long", paramType = "path")
    public ResultMessage registe();
}
