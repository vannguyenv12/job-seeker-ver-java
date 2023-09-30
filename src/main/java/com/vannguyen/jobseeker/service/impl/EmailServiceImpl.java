package com.vannguyen.jobseeker.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void fakeSendEmail() {
        String recipient = "<npnv.vn1@gmail.com>";
        String subject = "Hello, ${firstName}!";
        String template = "Hello, ${firstName}!\n\n"
                + "This is a message just for you, ${firstName} ${lastName}. "
                + "We hope you're having a great day!\n\n"
                + "Best regards,\n"
                + "The Spring Boot Team";

        sendEmail(recipient, subject, template);
    }
}
