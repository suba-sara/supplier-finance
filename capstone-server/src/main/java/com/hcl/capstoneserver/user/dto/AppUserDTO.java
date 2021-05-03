package com.hcl.capstoneserver.user.dto;

public class AppUserDTO {
    private String userId;

    public AppUserDTO() {
    }

    public AppUserDTO(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
