package com.hcl.capstoneserver.user.dto.views;

public class UserVerifiedDTO {
    private String userId;
    private Integer OTP;

    public UserVerifiedDTO(Integer userId, Integer OTP) {
        this.userId = userId;
        this.OTP = OTP;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOTP() {
        return OTP;
    }

    public void setOTP(Integer OTP) {
        this.OTP = OTP;
    }
}
