package com.hcl.capstoneserver.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvoiceDateOldException extends HttpClientErrorException {
    private final String FIELD = "Invoice Date";

    public InvoiceDateOldException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }

    public String getField() {
        return FIELD;
    }
}
