package com.hcl.capstoneserver.user.dto;

public class JwtWithTypeDTO {
    private String jwt;
    private String username;
    private String userType;

    public JwtWithTypeDTO() {
    }

    public JwtWithTypeDTO(String jwt, String userType, String username) {
        this.username = username;
        this.jwt = jwt;
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

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
