package com.hcl.capstoneserver.account.dto;

public class AccountVerifiedDTO {
    private String accountNumber;
    private String OTP;

    public AccountVerifiedDTO(String accountNumber, String OTP) {
        this.accountNumber = accountNumber;
        this.OTP = OTP;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
