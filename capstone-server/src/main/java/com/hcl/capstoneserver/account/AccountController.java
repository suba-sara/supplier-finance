package com.hcl.capstoneserver.account;

import com.hcl.capstoneserver.account.dto.AccountVerifiedDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/account-check")
    public ResponseEntity<Boolean> checkAccount(@Valid @RequestBody Integer accountNumber) {
        return new ResponseEntity<>(accountService.checkAccount(accountNumber), HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/account-verified")
    public ResponseEntity<Boolean> verifiedAccount(@Valid @RequestBody AccountVerifiedDTO accountVerifiedDTO) {
        return new ResponseEntity<>(accountService.verifiedAccount(accountVerifiedDTO), HttpStatus.OK);
    }
}
