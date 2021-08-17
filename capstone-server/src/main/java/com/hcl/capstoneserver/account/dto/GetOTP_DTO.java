package com.hcl.capstoneserver.account.dto;

import javax.validation.constraints.NotNull;

public class GetOTP_DTO {
    @NotNull
    private Integer accountNumber;

    public GetOTP_DTO() {
    }

    public GetOTP_DTO(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }
}
