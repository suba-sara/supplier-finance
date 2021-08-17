package com.hcl.capstoneserver.account;

import com.hcl.capstoneserver.account.dto.AccountVerifiedDTO;
import com.hcl.capstoneserver.account.entity.Account;
import com.hcl.capstoneserver.account.exception.AccountAlreadyHasUser;
import com.hcl.capstoneserver.account.exception.AccountNotFoundException;
import com.hcl.capstoneserver.account.exception.OTPTimedOut;
import com.hcl.capstoneserver.account.repositories.AccountRepository;
import com.hcl.capstoneserver.mail.sender.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final EmailService emailService;

    @Value("${otp.validity.time}")
    private Integer otpValidityTime;

    public AccountService(AccountRepository accountRepository, EmailService emailService) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
    }

    public Boolean getOTP(String accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (account.isPresent()) {
            Account acc = account.get();


            if (!acc.getVerified()) {
                // generate a six digit otp code
                String otp = String.valueOf(100000 + new Random().nextInt(999999));

                emailService.sendOtp(acc.getEmail(), otp);
                acc.setOTP(otp);
                acc.setOtpExpiredDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(otpValidityTime)));
                accountRepository.save(acc);
                return true;
            } else {
                throw new AccountAlreadyHasUser();
            }
        }

        throw new AccountNotFoundException();
    }

    public Boolean checkOTP(AccountVerifiedDTO accountVerifiedDTO) {
        Optional<Account> account = accountRepository.findById(accountVerifiedDTO.getAccountNumber());
        if (account.isPresent()) {
            Account acc = account.get();

            // verify otp time validity
            if (new Date().before(acc.getOtpExpiredDate())) {
                // verify otp code
                return acc.getOTP().equals(accountVerifiedDTO.getOTP());
            } else {
                throw new OTPTimedOut();
            }
        }
        throw new AccountNotFoundException();
    }

    public Boolean verifyAccount(AccountVerifiedDTO accountVerifiedDTO) {
        Optional<Account> account = accountRepository.findById(accountVerifiedDTO.getAccountNumber());
        if (account.isPresent()) {
            Account acc = account.get();
            if (new Date().before(acc.getOtpExpiredDate())) {
                if (acc.getOTP().equals(accountVerifiedDTO.getOTP())) {
                    acc.setVerified(true);
                    accountRepository.save(acc);
                    return true;
                }
            } else {
                throw new OTPTimedOut();
            }
        }
        throw new AccountNotFoundException();
    }
}
