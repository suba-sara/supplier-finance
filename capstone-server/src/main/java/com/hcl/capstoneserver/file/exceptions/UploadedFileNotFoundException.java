package com.hcl.capstoneserver.file.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UploadedFileNotFoundException extends RuntimeException {
    public UploadedFileNotFoundException(Integer id) {
        super(String.format("File with id %d not found", id));
    }

}
