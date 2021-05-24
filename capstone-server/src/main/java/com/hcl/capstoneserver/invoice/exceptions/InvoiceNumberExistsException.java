package com.hcl.capstoneserver.invoice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class InvoiceNumberExistsException extends HttpClientErrorException {
    public InvoiceNumberExistsException(Integer invoiceNumber) {
        super(HttpStatus.FORBIDDEN, String.format("Invoice number %s is not valid", invoiceNumber));
    }
}
