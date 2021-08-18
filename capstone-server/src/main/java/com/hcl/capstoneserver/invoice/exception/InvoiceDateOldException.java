package com.hcl.capstoneserver.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvoiceDateOldException extends HttpClientErrorException {
    public InvoiceDateOldException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST, "Invoice Date", null, null, null);
    }
}
