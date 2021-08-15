package com.hcl.capstoneserver.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtInvalidException extends HttpClientErrorException {
    public JwtInvalidException() {
        super("Jwt is invalid.", HttpStatus.FORBIDDEN, "Auth Token", null, null, null);
    }
}
