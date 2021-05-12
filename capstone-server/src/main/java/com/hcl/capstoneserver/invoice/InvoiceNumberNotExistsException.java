package com.hcl.capstoneserver.invoice;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class InvoiceNumberNotExistsException extends HttpClientErrorException {
    public InvoiceNumberNotExistsException(Integer invoiceNumber) {
        super(HttpStatus.FORBIDDEN, String.format("Invoice number %s is not valid", invoiceNumber));
    }
}
