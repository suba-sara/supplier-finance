package com.hcl.capstoneserver.user.dto;

public class SignInResponseDTO {
    private String jwt;

    public SignInResponseDTO() {
    }

    public SignInResponseDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
