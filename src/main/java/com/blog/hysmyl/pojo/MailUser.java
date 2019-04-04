package com.blog.hysmyl.pojo;

import lombok.Getter;

/**
 * @author Liu Mingyao
 * @since 2018-11-27 22:59
 * 邮件用户。包括发件人 收件人 抄送人 密送人.
 * //三个参数为:邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码(如"UTF-8")
 **/
@Getter
public class MailUser {
    private String address;
    private String personal;//昵称
    private String charset;

    public MailUser(String address, String personal, String charset) {
        this.address = address;
        this.personal = personal;
        this.charset = charset;
    }

    public MailUser(String address, String personal) {
        this.address = address;
        this.personal = personal;
        this.charset = "UTF-8";
    }
}
