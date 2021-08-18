package com.hcl.capstoneserver.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class OTPTimedOut extends HttpClientErrorException {
    public OTPTimedOut() {
        super(HttpStatus.BAD_REQUEST, "OTP code timed out.");
    }
}
