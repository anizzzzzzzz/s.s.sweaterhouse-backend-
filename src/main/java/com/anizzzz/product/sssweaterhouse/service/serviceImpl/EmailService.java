package com.anizzzz.product.sssweaterhouse.service.serviceImpl;

import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.EmailException;
import com.anizzzz.product.sssweaterhouse.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class EmailService implements IEmailService {
    private Logger logger = LoggerFactory.getLogger(EmailService.class);
    public static int noOfQuickServiceThreads = 20;

    /**
     * this statement create a thread pool of twenty threads
     * here we are assigning send mail task using ScheduledExecutorService.submit();
     */
    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String username, String subject, String message) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg=javaMailSender.createMimeMessage();
        msg.setFrom(new InternetAddress("S.S.SweaterHouse","S.S.Sweaterhouse","UTF-*"));
        msg.setRecipient(Message.RecipientType.TO,new InternetAddress(username));
        msg.setSubject(subject);
        msg.setContent(message,"text/html; charset=utf-8");
        quickService.submit(() -> {
            try{
                javaMailSender.send(msg);
            }
            catch(Exception ex){
                logger.error(ex.getMessage());
                throw new EmailException(ex.getMessage(),ex);
            }
        });
    }
}
