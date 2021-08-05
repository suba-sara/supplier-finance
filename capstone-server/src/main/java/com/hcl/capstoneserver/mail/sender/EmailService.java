package com.hcl.capstoneserver.mail.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Integer send(String receiver) {
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(receiver);
            messageHelper.setFrom(emailSender);
            messageHelper.setSubject("SHS BANK OTP CODE");
            messageHelper.setText(String.format("Your OTP Code: %d", OTP));
        };
        this.javaMailSender.send(message);
        return OTP;
    }
}
