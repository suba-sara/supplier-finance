package com.hcl.capstoneserver.user.dto;

public class AppUserWithPasswordDTO extends AppUserDTO implements WithPassword{
    private String password;

    public AppUserWithPasswordDTO() {
    }

    public AppUserWithPasswordDTO(String userId, String password) {
        super(userId);
        this.password = password;
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
