package com.blog.hysmyl.controller;

import com.alibaba.fastjson.JSON;
import com.blog.hysmyl.utils.BlogLog;
import com.blog.hysmyl.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Liu Mingyao
 * @Deprecated Editor相关操作的接口
 * @since 2018-11-12 18:21
 **/
@Controller
public class EditorController {
    @Autowired
    private BlogLog blogLog;


    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(HttpServletRequest request) {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        List<String> paths = new ArrayList<>();


        //检查是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            Date date;
            SimpleDateFormat dateFormat;
            String path = "";
            String clzPath = "";
            while (iter.hasNext()) {
                MultipartFile file;
                try {
                    //打jar之后的得到的是jar包上级目录 如c.jar在target下  得到的是target目录。没有打包在当前工程目录下 即class同级目录
                    clzPath = new File(".").getCanonicalPath();
                    //一次遍历所有文件
                    file = multiRequest.getFile(iter.next().toString());
                    if (file != null) {
                        date = new Date();
                        dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
                        String now = dateFormat.format(date);
                        //除去clzPath中的  file： (在打包后需要除去jar:file:   这是兼容做法)
                        path = clzPath + "/user-img/" + now + file.getOriginalFilename();
                        //user-img文件夹不存在就创建
                        File userImgDir = new File(clzPath + "/user-img");
                        if (!userImgDir.exists()) userImgDir.mkdirs();

                        paths.add("/user-img/" + now + file.getOriginalFilename());
                        //上传
                        file.transferTo(new File(path));
                    }
                } catch (Exception e) {
                    blogLog.exceptionLog(getClass(), "文件上传失败  -  获取到的项目根路径是clzPath:",clzPath, "\npath是：", path, "  -  ", e.getMessage());
                    e.printStackTrace();
                    return JSON.toJSONString(ResultMessage.wrongMsg("文件上传失败!"));
                } finally {
                    file = null;
                    date = null;
                    dateFormat = null;
                }
            }
            return JSON.toJSONString(ResultMessage.rightMsg(paths.toArray(), "上传成功!"));
        }
        return JSON.toJSONString(ResultMessage.wrongMsg("请选择需要上传的文件!"));
    }

}
