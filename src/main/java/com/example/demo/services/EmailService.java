package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // FIX Bug 3: Injecting the configured sender address so we can set `From` on every mail.
    // Gmail's SMTP server requires the `From` header to match the authenticated user.
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body){
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(fromEmail); // FIX Bug 3: was missing — Gmail rejects mail with no/wrong From header
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
        } catch (Exception e) {
            log.error("Exception while sendMail ", e);
        }
    }

}
