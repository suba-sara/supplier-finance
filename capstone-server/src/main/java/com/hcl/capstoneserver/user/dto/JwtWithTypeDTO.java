package com.hcl.capstoneserver.user.dto;

public class JwtWithTypeDTO {
    private String jwt;
    private String userType;

    public JwtWithTypeDTO() {
    }

    public JwtWithTypeDTO(String jwt, String userType) {
        this.jwt = jwt;
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
