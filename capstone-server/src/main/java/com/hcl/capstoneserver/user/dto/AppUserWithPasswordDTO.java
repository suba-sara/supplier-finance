package com.hcl.capstoneserver.user.dto;

public class AppUserWithPasswordDTO {
    private String userId;
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

    public String getPassword() {
        return password;
    }
}
