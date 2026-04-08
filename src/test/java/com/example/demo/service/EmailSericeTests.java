package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.services.EmailService;

@SpringBootTest
public class EmailSericeTests {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail(){
        emailService.sendEmail("ankit@dsvcorp.com.au", "Testing Java Mail Sender", "Hii, app kaise hain");
    }
}
