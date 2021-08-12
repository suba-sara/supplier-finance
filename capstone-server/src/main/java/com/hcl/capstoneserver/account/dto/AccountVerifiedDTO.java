package com.hcl.capstoneserver.account.dto;

public class AccountVerifiedDTO {
    private Integer accountNumber;
    private Integer otp;

    public AccountVerifiedDTO(Integer accountNumber, Integer otp) {
        this.accountNumber = accountNumber;
        this.otp = otp;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
