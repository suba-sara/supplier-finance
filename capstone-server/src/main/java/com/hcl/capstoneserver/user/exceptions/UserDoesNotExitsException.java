package com.hcl.capstoneserver.user.exceptions;

import com.hcl.capstoneserver.user.UserType;

public class UserDoesNotExitsException extends RuntimeException {
    public UserDoesNotExitsException(String username, UserType userType) {
        super(String.format("%s with username %s not exits!", userType, username));
    }
}
