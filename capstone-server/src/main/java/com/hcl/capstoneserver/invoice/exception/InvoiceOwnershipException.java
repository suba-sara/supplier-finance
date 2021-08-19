package com.hcl.capstoneserver.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class InvoiceOwnershipException extends HttpClientErrorException {
    public InvoiceOwnershipException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
