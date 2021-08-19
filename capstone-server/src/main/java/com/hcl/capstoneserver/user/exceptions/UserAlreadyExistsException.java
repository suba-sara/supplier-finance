package com.hcl.capstoneserver.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends HttpClientErrorException {
    public UserAlreadyExistsException(String username) {
        super(HttpStatus.BAD_REQUEST, String.format("User with username %s already exits.", username));
    }
}
