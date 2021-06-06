package com.hcl.capstoneserver.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvoiceStatusException extends HttpClientErrorException {
    private final String FIELD;

    public InvoiceStatusException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
        this.FIELD = "Invoice Status";
    }

    public String getField() {
        return FIELD;
    }
}
