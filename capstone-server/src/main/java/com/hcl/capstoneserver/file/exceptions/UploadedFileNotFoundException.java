package com.hcl.capstoneserver.file.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UploadedFileNotFoundException extends HttpClientErrorException {
    public UploadedFileNotFoundException(Integer id) {
        super(HttpStatus.NOT_FOUND, String.format("File with id %d not found", id));
    }

}
