package com.hcl.capstoneserver.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ValidationErrorException extends HttpClientErrorException {
    public ValidationErrorException(HttpStatus statusCode) {
        super(statusCode);
    }
}
