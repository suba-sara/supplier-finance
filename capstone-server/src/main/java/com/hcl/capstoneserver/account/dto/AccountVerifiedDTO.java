package com.hcl.capstoneserver.account.dto;

public class AccountVerifiedDTO {
    private Integer accountNumber;
    private Integer OTP;

    public AccountVerifiedDTO(Integer accountNumber, Integer OTP) {
        this.accountNumber = accountNumber;
        this.OTP = OTP;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getOTP() {
        return OTP;
    }

    public void setOTP(Integer OTP) {
        this.OTP = OTP;
    }
}
