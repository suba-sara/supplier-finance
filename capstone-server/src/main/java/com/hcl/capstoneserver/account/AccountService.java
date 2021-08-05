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
import java.util.Objects;
import java.util.Optional;
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

    public Boolean getOTP(Integer accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (account.isPresent()) {
            Account acc = account.get();
            if (!acc.getVerified()) {
                int OTP = emailService.send(acc.getEmail());
                acc.setOTP(OTP);
                acc.setOtpExpiredDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(otpValidityTime)));
                accountRepository.save(acc);
                return true;
            } else {
                throw new AccountAlreadyHasUser();
            }
        }

        throw new AccountNotFoundException();
    }

    public Boolean verifyAccount(AccountVerifiedDTO accountVerifiedDTO) {
        Optional<Account> account = accountRepository.findById(accountVerifiedDTO.getAccountNumber());
        if (account.isPresent()) {
            Account acc = account.get();
            if (new Date().before(acc.getOtpExpiredDate())) {
                if (Objects.equals(acc.getOTP(), accountVerifiedDTO.getOtp())) {
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
