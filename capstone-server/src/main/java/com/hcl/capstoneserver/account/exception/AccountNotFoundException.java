package com.hcl.capstoneserver.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends HttpClientErrorException {

    public AccountNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Bank account not found.");
    }
}
