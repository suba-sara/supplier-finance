package com.hcl.capstoneserver.account;

import com.hcl.capstoneserver.account.dto.AccountVerifiedDTO;
import com.hcl.capstoneserver.account.dto.GetOtpResponseDTO;
import com.hcl.capstoneserver.account.entity.Account;
import com.hcl.capstoneserver.account.exception.AccountAlreadyHasUser;
import com.hcl.capstoneserver.account.exception.AccountNotFoundException;
import com.hcl.capstoneserver.account.exception.OTPTimedOut;
import com.hcl.capstoneserver.account.repositories.AccountRepository;
import com.hcl.capstoneserver.mail.sender.EmailService;
import com.hcl.capstoneserver.user.dto.CheckValidDTO;
import com.hcl.capstoneserver.util.EmailHideParts;
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

    @Value("${test.otp.seed}")
    private Integer testOtpSeed;

    @Value("${spring.env}")
    private String env;

    public AccountService(AccountRepository accountRepository, EmailService emailService) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
    }

    public GetOtpResponseDTO getOTP(String accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (account.isPresent()) {
            Account acc = account.get();


            if (!acc.getVerified()) {
                // generate a six digit otp code
                Random r;
                if (env.equals("test")) {
                    r = new Random(testOtpSeed);
                } else {
                    r = new Random();
                }

                String otp = String.valueOf(100000 + r.nextInt(999999));

                emailService.sendOtp(acc.getEmail(), otp);
                acc.setOTP(otp);
                acc.setOtpExpiredDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(otpValidityTime)));
                accountRepository.save(acc);

                return new GetOtpResponseDTO(new EmailHideParts(acc.getEmail()).hide());
            } else {
                throw new AccountAlreadyHasUser();
            }
        }

        throw new AccountNotFoundException();
    }

    public CheckValidDTO checkOTP(AccountVerifiedDTO accountVerifiedDTO) {
        Optional<Account> account = accountRepository.findById(accountVerifiedDTO.getAccountNumber());
        if (account.isPresent()) {
            Account acc = account.get();

            // verify otp time validity
            if (new Date().before(acc.getOtpExpiredDate())) {
                // verify otp code
                return new CheckValidDTO(acc.getOTP().equals(accountVerifiedDTO.getOTP()), null);
            } else {
                throw new OTPTimedOut();
            }
        }
        throw new AccountNotFoundException();
    }

    public CheckValidDTO verifyAccount(AccountVerifiedDTO accountVerifiedDTO) {
        Optional<Account> account = accountRepository.findById(accountVerifiedDTO.getAccountNumber());
        if (account.isPresent()) {
            Account acc = account.get();
            if (new Date().before(acc.getOtpExpiredDate())) {
                if (acc.getOTP().equals(accountVerifiedDTO.getOTP())) {
                    acc.setVerified(true);
                    accountRepository.save(acc);
                    return new CheckValidDTO(true, null);
                }
            } else {
                throw new OTPTimedOut();
            }
        }
        throw new AccountNotFoundException();
    }
}
