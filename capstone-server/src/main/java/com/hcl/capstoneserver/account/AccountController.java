package com.hcl.capstoneserver.account;

import com.hcl.capstoneserver.account.dto.AccountVerifiedDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/account/get-otp/{accountNumber}")
    public ResponseEntity<Boolean> getOTP(@PathVariable Integer accountNumber) {
        return new ResponseEntity<>(accountService.getOTP(accountNumber), HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/account/verify")
    public ResponseEntity<Boolean> verifyAccount(@Valid @RequestBody AccountVerifiedDTO accountVerifiedDTO) {
        return new ResponseEntity<>(accountService.verifyAccount(accountVerifiedDTO), HttpStatus.OK);
    }
}
