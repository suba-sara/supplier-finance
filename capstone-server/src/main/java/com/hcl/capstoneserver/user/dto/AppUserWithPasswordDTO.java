package com.hcl.capstoneserver.user.dto;

import javax.validation.constraints.NotBlank;

public class AppUserWithPasswordDTO implements WithPassword{
    @NotBlank(message = "userId is required")
    private String userId;

    @NotBlank(message = "password is required")
    private String password;

    public AppUserWithPasswordDTO() {
    }

    public AppUserWithPasswordDTO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
