package com.hcl.capstoneserver.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountAlreadyHasUser extends HttpClientErrorException {

    public AccountAlreadyHasUser() {
        super(HttpStatus.BAD_REQUEST, "You already have a account, please login.");
    }
}
