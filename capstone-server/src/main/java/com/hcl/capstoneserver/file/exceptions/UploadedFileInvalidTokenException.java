package com.hcl.capstoneserver.file.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UploadedFileInvalidTokenException extends RuntimeException {
    public UploadedFileInvalidTokenException() {
        super("Invalid token");
    }

}
