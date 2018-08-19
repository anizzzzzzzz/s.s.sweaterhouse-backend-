package com.anizzzz.product.sssweaterhouse.service.serviceImpl;

import com.anizzzz.product.sssweaterhouse.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService implements IEmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void SendMail(String username, String subject, String message) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg=javaMailSender.createMimeMessage();
        msg.setFrom(new InternetAddress("S.S.SweaterHouse","S.S.Sweaterhouse","UTF-*"));
        msg.setRecipient(Message.RecipientType.TO,new InternetAddress(username));
        msg.setSubject(subject);
        msg.setContent(message,"text/html; charset=utf-8");
        javaMailSender.send(msg);
    }
}
