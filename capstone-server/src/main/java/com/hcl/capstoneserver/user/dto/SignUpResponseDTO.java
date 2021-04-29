package com.hcl.capstoneserver.user.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class SignUpResponseDTO {
    private String username;
    private String userType;

    public SignUpResponseDTO() {
    }

    public SignUpResponseDTO(String username, String userType) {
        this.username = username;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
