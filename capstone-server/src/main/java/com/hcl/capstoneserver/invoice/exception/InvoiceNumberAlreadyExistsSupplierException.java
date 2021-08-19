package com.hcl.capstoneserver.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvoiceNumberAlreadyExistsSupplierException extends HttpClientErrorException {
    public InvoiceNumberAlreadyExistsSupplierException() {
        super(HttpStatus.BAD_REQUEST, "An invoice number already exists for this supplier.");
    }
}
