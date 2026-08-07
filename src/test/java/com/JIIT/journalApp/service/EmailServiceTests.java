package com.JIIT.journalApp.service;

import com.JIIT.journalApp.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("parthkartik125@gmail.com","Testing Java mail sender","Hi, aaap Kaise hain?");
    }

}
