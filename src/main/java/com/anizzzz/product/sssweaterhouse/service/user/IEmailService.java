package com.anizzzz.product.sssweaterhouse.service.user;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IEmailService {
    void sendMail(String username, String subject, String message) throws MessagingException, UnsupportedEncodingException;
}
