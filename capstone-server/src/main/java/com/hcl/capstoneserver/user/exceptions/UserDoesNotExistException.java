package com.hcl.capstoneserver.user.exceptions;

import com.hcl.capstoneserver.user.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDoesNotExistException extends HttpClientErrorException {
    private final String field;

    public UserDoesNotExistException(UserType userType, String field) {
        super(String.format("This %s is not exist.", userType), HttpStatus.BAD_REQUEST, field, null, null, null);
        this.field = field;
    }

    public final String getField() {
        return field;
    }
}
