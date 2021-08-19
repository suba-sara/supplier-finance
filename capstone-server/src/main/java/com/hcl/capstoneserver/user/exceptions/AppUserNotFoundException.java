package com.hcl.capstoneserver.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AppUserNotFoundException extends HttpClientErrorException {
    public AppUserNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "User not found.");
    }
}
