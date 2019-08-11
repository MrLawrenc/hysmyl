package com.blog.hysmyl.utils.email;

import com.blog.hysmyl.entity.MyEmail;
import com.blog.hysmyl.pojo.MailUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * @author Liu Mingyao
 * @Deprecated 邮件发送信息类
 * @since 2018-11-27 23:46
 **/
@AllArgsConstructor
@Getter
public class SendMail {

    public String myEmailPassword;
    // 网易126邮箱的 SMTP 服务器地址为: smtp.163.com。其他邮箱可以通过构造器传入
    public String myEmailSMTPHost = "smtp.163.com";

    /**
     * @author Liu Ming
     * @deprecated 默认为网易邮箱 SMTP
     */
    public SendMail(String myEmailPassword) {
        this.myEmailPassword = myEmailPassword;
    }

    /**
     * @param session 和服务器交互的会话
     * @param myEmail 有间相关信息类
     * @deprecated 创建一封只包含文本的简单邮件
     */
    public MimeMessage createMimeMessage(Session session, MyEmail myEmail, boolean isAddRecipient, boolean isAddCC, boolean isAddBCC) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        MailUser fromUser = myEmail.getFromUser();
        message.setFrom(new InternetAddress(fromUser.getAddress(), fromUser.getPersonal(), fromUser.getCharset()));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        MailUser recipientUser = myEmail.getRecipientUser();
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientUser.getAddress(), recipientUser.getPersonal(), recipientUser.getCharset()));

        if (isAddRecipient) addRecipient(myEmail.getAddRecipient(), message);
        if (isAddCC) addRecipient(myEmail.getAddCCRecipient(), message);
        if (isAddBCC) addRecipient(myEmail.getAddBCCRecipient(), message);

        message.setSubject(myEmail.getSubject(), myEmail.getSubjectCharset());
        message.setContent(myEmail.getContent(), myEmail.getEmailContentCharset());
        message.setSentDate(myEmail.getSentDate());
        message.saveChanges();
        return message;
    }


    /**
     * @param emails 其他收件人信息集合
     * @deprecated 添加其他收件人
     */
    public void addRecipient(List<MailUser> emails, MimeMessage message) throws Exception {
        for (MailUser mailUser : emails) {
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mailUser.getAddress(), mailUser.getPersonal(), mailUser.getCharset()));
        }

    }

    /**
     * @param emails 抄送人信息
     * @author Liu Ming
     * @deprecated 添加抄送人：其实和添加多个收件人一样，只不过意义不同，只是通知一下。但是收件人知道抄送人
     */
    public void addCCRecipient(List<MailUser> emails, MimeMessage message) throws Exception {
        for (MailUser email : emails) {
            message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(email.getAddress(), email.getPersonal(), email.getCharset()));
        }
    }

    /**
     * @param emails 密送人信息
     * @author Liu Ming
     * @deprecated 添加密送人：收件人不知道邮件还发给了密送人
     */
    public void addBCCRecipient(List<MailUser> emails, MimeMessage message) throws Exception {
        for (MailUser email : emails) {
            message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(email.getAddress(), email.getPersonal(), email.getCharset()));
        }
    }
}
