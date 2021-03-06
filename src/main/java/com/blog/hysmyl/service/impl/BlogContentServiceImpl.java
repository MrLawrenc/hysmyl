package com.blog.hysmyl.service.impl;

import com.blog.hysmyl.VO.BlogContentVO;
import com.blog.hysmyl.mapper.BlogContentMapper;
import com.blog.hysmyl.pojo.BlogContent;
import com.blog.hysmyl.service.BlogContentService;
import com.blog.hysmyl.utils.BlogLog;
import com.blog.hysmyl.utils.ResultMessage;
import com.blog.hysmyl.utils.StringUtils;
import com.blog.hysmyl.utils.redis.RedisUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Liu Mingyao
 * @since 2018-11-24 11:03
 **/
@Service
public class BlogContentServiceImpl implements BlogContentService {
    @Autowired
    private BlogContentMapper mapper;
    @Autowired
    private BlogLog blogLog;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @author Liu Ming
     * @description 发表博客，在发表成功之后需要更新redis缓存中的blog列表数据
     */
    @Override
    public ResultMessage pushBlog(BlogContent blogContent) {

        if (StringUtils.isEmpty(blogContent.getContent())) return ResultMessage.wrongMsg("内容不能为空!");
        blogContent.setCreateTime(new Date());


        //在发表博客之前需要将图片同步到blog-img文件夹里面，并且修改博客的内容中的图片路径，更换到新的blog-img文件夹（目的是为了定时清理用户上传之后在编辑器又删除的图片）
        String content = blogContent.getContent();
        //使用正则表达式提取出博客html中的所有<img\>标签
        Pattern pattern = Pattern.compile("<img .+?>");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String imgStr = matcher.group();
            //排除不需要复制的网络图片
            if (imgStr.contains("http")) continue;
            String newImgStr = imgStr.replace("user-img", "blog-img");

            //进行文件复制，从user-img复制到blog-img文件夹
            String replace = imgStr.replace("<img src=\"", "");

            String imgPath = replace.substring(0, replace.indexOf("\""));
            String realImgPath = "";
            try {
                realImgPath = new File(".").getCanonicalPath() + imgPath;
                FileUtils.copyFile(new File(realImgPath), new File(realImgPath.replace("user-img", "blog-img")));

                //没有发生异常:完成博客html的内容content替换(更改图片路径)
                content = content.replace(imgStr, newImgStr);
                blogLog.infoLog(getClass(), "图片复制正常,路径由" + realImgPath + "更改为==》" + realImgPath.replace("user-img", "blog-img"));
            } catch (IOException e) {
                blogLog.exceptionLog(getClass(), "文件复制异常:\n原文件路径:" + realImgPath + "\n" + e.getMessage());
            }
        }

        blogContent.setContent(content);

        if (mapper.add(blogContent) == 0) return ResultMessage.wrongMsg("发表失败!");

        //发表成功则更新redis缓存
        redisUtil.pushListOne("blogList", blogContent);
        blogLog.infoLog(getClass(), "更新redis缓存成功" + blogContent);
        return ResultMessage.rightMsg("发表成功!");
    }


    /**
     * 得到该用户所有的博客内容
     * <p>引入redis缓存，先查缓存，kafka会监听新发布的博客，如果有新发布的内容会更新redis缓存</>
     *
     * @author Liu Ming
     **/
    @Override
    public List<BlogContentVO> getBlogList() {
        
        List<Object> list = redisUtil.listAll("blogList");
        List<BlogContent> blogContents = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            blogContents.add((BlogContent) list.get(i));
        }
        List<BlogContentVO> blogContentVOS = new ArrayList<>();

        //从数据库查询，上面是从redis查询
        // List<BlogContent> blogContents = mapper.getBlogList();
        //if (blogContents == null) return null;

        blogContents.forEach(blogContent -> {
            Integer id = blogContent.getId();
            //消除content中的html标签
            String noLabelContent = blogContent.getContent().replaceAll("</?[^>]+>", "");
            //如果没有更新时间就使用创建时间
            BlogContentVO blogContentVO = blogContent.getUpdateTime() == null ? new BlogContentVO(id, blogContent.getTitle(), blogContent.getCreateTime(), noLabelContent) : new BlogContentVO(id, blogContent.getTitle(), blogContent.getUpdateTime(), noLabelContent);
            blogContentVOS.add(blogContentVO);
        });
        return blogContentVOS;
    }

    /**
     * 查看某一篇博客内容
     */
    @Override
    public BlogContent getBlog(Integer id) {
        BlogContent blogContent = mapper.getBlog(id);
        return blogContent == null ? null : blogContent;
    }
}
