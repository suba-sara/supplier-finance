package com.hcl.capstoneserver.user.dto;

public class PersonWithPasswordDTO extends PersonDTO implements WithPassword {
    private String password;


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
