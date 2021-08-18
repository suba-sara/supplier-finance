package com.hcl.capstoneserver.user.dto;

import javax.validation.constraints.NotBlank;

public class UserVerifiedDTO {
    @NotBlank(message = "userId is required")
    private String userId;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "OTP is required")
    private String otp;

    public UserVerifiedDTO(String userId, String password, String otp) {
        this.userId = userId;
        this.password = password;
        this.otp = otp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOTP() {
        return otp;
    }

    public void setOTP(String otp) {
        this.otp = otp;
    }
}
