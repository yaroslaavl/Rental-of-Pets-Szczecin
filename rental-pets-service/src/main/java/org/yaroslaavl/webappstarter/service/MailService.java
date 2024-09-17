package org.yaroslaavl.webappstarter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private final String fromEmail;

    public MailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    @Async
    public void send(String to,
                     String subject,
                     String text){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(text);

            mailSender.send(mailMessage);
        } catch (Exception e){
            log.info("Exception: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
