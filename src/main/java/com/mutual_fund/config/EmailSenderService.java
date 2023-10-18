package com.mutual_fund.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

        log.debug("sent");
//        System.out.println("sent");
    }
}
