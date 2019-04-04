package com.blog.hysmyl.entity;

import com.blog.hysmyl.pojo.MailUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author Liu Mingyao
 * @since 2018-11-25 21:18
 * @deprecated 邮件实体类
 **/
@Setter
@Getter
@Accessors(chain = true)
public class MyEmail {
    //发件人信息
    private MailUser fromUser;
    //收件人信息
    private MailUser recipientUser;

    //邮件主题和编码
    private String subject;
    private String subjectCharset;
    //邮件内容和发送时间
    private String content;
    private Date sentDate;
    //邮件正文编码,默认是支持html代码:text/html;charset=UTF-8.
    private String emailContentCharset;

    //其他收件人
    private List<MailUser> addRecipient;
    //抄送人
    private List<MailUser> addCCRecipient;
    //密送人
    private List<MailUser> addBCCRecipient;

    /**
     * @author Liu Ming
     * @deprecated 单个收件人，无抄送人和密送人的构造器
     */
    public MyEmail(MailUser fromUser, MailUser recipientUser, String subject, String subjectCharset, String content, Date sentDate, String emailContentCharset) {
        this.fromUser = fromUser;
        this.recipientUser = recipientUser;
        this.subject = subject;
        this.subjectCharset = subjectCharset;
        this.content = content;
        this.sentDate = sentDate;
        this.emailContentCharset = emailContentCharset;
    }

    /**
     * @param fromUser      发件人信息
     * @param recipientUser 收件人信息
     * @param subject       邮件主题
     * @param content       邮件内容
     * @param sentDate      发送时间
     * @author Liu Ming
     * @deprecated 默认邮件主题和内容的字符编码的构造器
     */
    public MyEmail(MailUser fromUser, MailUser recipientUser, String subject, String content, Date sentDate) {
        this.fromUser = fromUser;
        this.recipientUser = recipientUser;
        this.subject = subject;
        this.content = content;
        this.sentDate = sentDate;
        this.emailContentCharset = "text/html;charset=UTF-8";
        this.subjectCharset = "UTF-8";
    }
}
