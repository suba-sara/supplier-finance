package com.hcl.capstoneserver.account;

import com.hcl.capstoneserver.AccountStatus;
import com.hcl.capstoneserver.account.dto.AccountVerifiedDTO;
import com.hcl.capstoneserver.account.entity.Account;
import com.hcl.capstoneserver.account.exception.AccountNotFoundException;
import com.hcl.capstoneserver.account.repositories.AccountRepository;
import com.hcl.capstoneserver.mail.sender.EmailService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final EmailService emailService;

    public AccountService(AccountRepository accountRepository, EmailService emailService) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
    }

    public Boolean checkAccount(Integer accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);
        if (account.isPresent()) {
            emailService.send(account.get().getEmail(), account.get().getAccountNumber());
            return true;
        } else {
            throw new AccountNotFoundException();
        }
    }

    public Boolean verifiedAccount(AccountVerifiedDTO accountVerifiedDTO) {
        Optional<Account> account = accountRepository.findById(accountVerifiedDTO.getAccountNumber());
        if (account.isPresent()) {
            if (account.get().getOtpExpiredDate().before(new Date())) {
                if (Objects.equals(account.get().getOTP(), accountVerifiedDTO.getOtp())) {
                    account.get().setAccountStatus(AccountStatus.VERIFIED);
                    accountRepository.save(account.get());
                    return true;
                }
            }
        }
        return false;
    }
}
