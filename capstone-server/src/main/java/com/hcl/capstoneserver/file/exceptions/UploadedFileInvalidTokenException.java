package com.hcl.capstoneserver.file.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UploadedFileInvalidTokenException extends HttpClientErrorException {
    public UploadedFileInvalidTokenException() {
        super(HttpStatus.BAD_REQUEST, "Invalid token");
    }

}
