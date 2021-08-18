package com.hcl.capstoneserver.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class InvoiceDeleteRestrictedException extends HttpClientErrorException {

    public InvoiceDeleteRestrictedException() {
        super(HttpStatus.FORBIDDEN, "Invoice Delete Restricted");
    }
}
