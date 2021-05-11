package com.hcl.capstoneserver.user.dto;

public class JwtRefreshDto {
    private String jwt;

    public JwtRefreshDto() {

    }

    public JwtRefreshDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
