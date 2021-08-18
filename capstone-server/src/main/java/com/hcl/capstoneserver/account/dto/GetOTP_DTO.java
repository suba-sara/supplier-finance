package com.hcl.capstoneserver.account.dto;

import javax.validation.constraints.Pattern;

public class GetOTP_DTO {
    @Pattern(regexp = "[\\d]{8}", message = "accountNumber is invalid")
    private String accountNumber;

    public GetOTP_DTO() {
    }

    public GetOTP_DTO(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
