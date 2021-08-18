package com.hcl.capstoneserver.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ForbiddenException extends HttpClientErrorException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "You dont have permission to access this content");
    }
}
