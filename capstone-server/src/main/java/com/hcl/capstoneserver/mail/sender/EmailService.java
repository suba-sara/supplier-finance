package com.hcl.capstoneserver.mail.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    @Autowired
    private Environment environment;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOtp(String receiver, String otp) {
        System.out.println(Arrays.toString(environment.getActiveProfiles()));

        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(receiver);
            messageHelper.setFrom(emailSender);
            messageHelper.setSubject("SHS Bank's Supplier Finance Verification Code");
            messageHelper.setText(String.format("Your Verification Code is: %s", otp));
        };
        this.javaMailSender.send(message);
    }

}
