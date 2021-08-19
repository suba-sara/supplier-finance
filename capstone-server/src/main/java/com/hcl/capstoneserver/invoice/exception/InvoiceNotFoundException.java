package com.hcl.capstoneserver.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvoiceNotFoundException extends HttpClientErrorException {
    public InvoiceNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
