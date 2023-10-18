package com.example.mfu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmai, String sub, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hclmutualfundtest@gmail.com");
        message.setTo(toEmai);
        message.setText(body);
        message.setSubject(sub);

        mailSender.send(message);

        System.out.println("sent");
    }
}
