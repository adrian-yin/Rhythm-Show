package com.adrianyin.rhythmshow.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

// 提供发送邮箱验证码的方法
public class MailUtils {

    // 发件人邮箱
    private static final String EMAIL_NAME = "rhythm_show@163.com";
    // 授权码
    private static final String AUTHENTICATION_CODE = "DFCIOKLMTOIVRACZ";
    // SMTP服务器
    private static final String HOST = "smtp.163.com";

    public static void sendVerifyCode(String email, String code) {

        Properties properties = System.getProperties();
        // 设置邮箱服务器
        properties.setProperty("mail.smtp.host", HOST);
        // 打开认证
        properties.setProperty("mail.smtp.auth", "true");

        try {
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 配置邮箱账号，授权码
                    return new PasswordAuthentication(EMAIL_NAME, AUTHENTICATION_CODE);
                }
            });

            // 邮件消息设置
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_NAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("炫·律验证码");
            String content = "<html><head></head><body>您的验证码为<b>" + code + "</b></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            System.out.println("验证码邮件已成功发送！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
