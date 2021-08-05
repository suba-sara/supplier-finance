package com.hcl.capstoneserver.mail.sender;

import com.hcl.capstoneserver.account.entity.Account;
import com.hcl.capstoneserver.account.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final AccountRepository accountRepository;

    @Value("${spring.mail.username}")
    String emailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, AccountRepository accountRepository) {
        this.javaMailSender = javaMailSender;
        this.accountRepository = accountRepository;
    }

    private Integer _generateOTP(Integer accountNumber) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        Optional<Account> account = accountRepository.findById(accountNumber);
        account.ifPresent(value -> {
            value.setOTP(otp);
            value.setOtpExpiredDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)));
            accountRepository.save(value);
        });
        return otp;
    }

    public void send(String receiver, Integer accountNumber) {
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(receiver);
            messageHelper.setFrom(emailSender);
            messageHelper.setSubject("SHS BANK OTP CODE");
            messageHelper.setText(String.format("Your OTP Code: %d", _generateOTP(accountNumber)));
        };
        this.javaMailSender.send(message);
    }
}
