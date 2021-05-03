package com.hcl.capstoneserver.user.dto;

public class AppUserWithPasswordDTO extends AppUserDTO {
    private String password;


    public AppUserWithPasswordDTO() {
    }

    public AppUserWithPasswordDTO(String userId, String password) {
        super(userId);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
