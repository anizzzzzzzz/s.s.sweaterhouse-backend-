package com.anizzzz.product.sssweaterhouse.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IEmailService {
    void SendMail(String username, String subject, String message) throws MessagingException, UnsupportedEncodingException;
}