package com.hcl.capstoneserver.account.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Account {
    @Id
    private Integer accountNumber;
    @NotNull
    private String accountHolderName;
    @NotNull
    private String email;
    private Integer OTP;
    private Date otpExpiredDate;
    private Boolean isVerified = false;

    public Account() {
    }

    public Account(Integer accountNumber, String accountHolderName, String email) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.email = email;
    }

    public Account(Integer accountNumber, String accountHolderName, String email, Integer OTP, Date otpExpiredDate, Boolean isVerified) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.email = email;
        this.OTP = OTP;
        this.otpExpiredDate = otpExpiredDate;
        this.isVerified = isVerified;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return accountHolderName;
    }

    public void setName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOTP() {
        return OTP;
    }

    public void setOTP(Integer OTP) {
        this.OTP = OTP;
    }

    public Date getOtpExpiredDate() {
        return otpExpiredDate;
    }

    public void setOtpExpiredDate(Date otpExpiredDate) {
        this.otpExpiredDate = otpExpiredDate;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }
}
