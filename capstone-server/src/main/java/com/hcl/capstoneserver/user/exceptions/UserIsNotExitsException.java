package com.hcl.capstoneserver.user.exceptions;

import com.hcl.capstoneserver.user.UserType;

public class UserIsNotExitsException extends RuntimeException {
    public UserIsNotExitsException(String username, UserType userType) {
        super(String.format("%s with username %s not exits!", userType, username));
    }
}
