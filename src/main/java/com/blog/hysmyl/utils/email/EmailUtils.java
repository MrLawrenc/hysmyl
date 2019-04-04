package com.blog.hysmyl.utils.email;

import com.alibaba.fastjson.JSON;
import com.blog.hysmyl.entity.MyEmail;
import com.blog.hysmyl.pojo.MailUser;
import com.blog.hysmyl.utils.BlogLog;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @author Liu Mingyao
 * @since 2018-11-28 21:12
 * @Deprecated 邮件相关操作  @Value注解 :null 后的null表示在配置中未配置属性的时候 默认值为null字符
 **/
@Component
@Setter
@Getter
public class EmailUtils {
    @Autowired
    private BlogLog log;

    @Value("${email.from.account:null}")
    private String account;
    @Value("${email.from.password:null}")
    private String password;
    @Value("${email.from.fromPersonal:null}")
    private String fromPersonal;


    @Value("${email.others.recipient.accounts:null}")
    private String recipientAccounts[];
    @Value("${email.others.recipient.personals:null}")
    private String recipientPersonals[];

    @Value("${email.others.CC.accounts:null}")
    private String CCAccounts[];
    @Value("${email.others.CC.personals:null}")
    private String CCPersonals[];

    @Value("${email.others.BCC.accounts:null}")
    private String BCCAccounts[];
    @Value("${email.others.BCC.personals:null}")
    private String BCCPersonals[];

    /**
     * @param isAddRecipient 是否添加收件人
     * @param isAddCC        是否添加抄送人
     * @param isAddBCC       是否添加密送人
     * @author Liu Ming
     * @deprecated 发送邮件前的准备
     */
    public boolean ready4Email(MyEmail myEmail, boolean isAddRecipient, boolean isAddCC, boolean isAddBCC) throws Exception {

        //mars943903861@163.com 邮箱的账户密码
        SendMail sendMail = new SendMail(password);
        Session session = getSession(sendMail.getMyEmailSMTPHost());
        MimeMessage message;
        try {
            message = sendMail.createMimeMessage(session, myEmail, isAddRecipient, isAddCC, isAddBCC);
        } catch (Exception e) {
            throw new RuntimeException("创建基本邮件信息失败!");
        }

        Transport transport = session.getTransport();
        transport.connect(myEmail.getFromUser().getAddress(), sendMail.myEmailPassword);

        // 6. 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();

        return true;
    }

    /**
     * @author Liu Ming
     * @deprecated 获取session
     */
    protected Session getSession(String host) {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);

        // 设置为debug模式, 可以查看详细的发送 log
        // session.setDebug(true);
        return session;
    }

    /**
     * @author Liu Ming
     * @deprecated 发送邮件
     */
    public void sendEmail(String title, String content) throws Exception {
        MailUser from = new MailUser(account, fromPersonal);

        if (recipientAccounts == null || recipientPersonals == null) {
            throw new RuntimeException("收件人账户或密码不能为空!");
        }
        MailUser recipient = new MailUser(recipientAccounts[0], recipientPersonals[0]);


        MyEmail my = new MyEmail(from, recipient, title, content + new Date() + "!" + "==》" + "☺", new Date());

        if (CCAccounts != null && CCPersonals != null && CCAccounts.length == CCPersonals.length) {
            List<MailUser> CC = new ArrayList<>();
            for (int i = 0; i < CCAccounts.length; i++) {
                MailUser mailUser = new MailUser(CCAccounts[i], CCPersonals[i]);
                CC.add(mailUser);
            }
            my.setAddCCRecipient(CC);
            ready4Email(my, false, true, false);
            return;
        }

        if (BCCAccounts != null && BCCPersonals != null && BCCAccounts.length == BCCPersonals.length) {
            List<MailUser> BCC = new ArrayList<>();
            for (int i = 0; i < BCCAccounts.length; i++) {
                MailUser mailUser = new MailUser(BCCAccounts[i], BCCPersonals[i]);
                BCC.add(mailUser);
            }
            my.setAddBCCRecipient(BCC);
            ready4Email(my, false, false, true);
            return;
        }
        ready4Email(my, false, false, false);
    }
}


