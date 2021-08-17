package com.hcl.capstoneserver.account.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Account {
    @Id
    private String accountNumber;
    @NotNull
    private String accountHolderName;
    private Float interestRate;
    @NotNull
    private String email;
    private String OTP;
    private Date otpExpiredDate;
    private Boolean isVerified = false;

    public Account() {
    }

    public Account(String accountNumber, String accountHolderName, String email, Float interestRate) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.email = email;
        this.interestRate = interestRate;
    }

    public Account(String accountNumber, String accountHolderName, Float interestRate, String email, String OTP, Date otpExpiredDate, Boolean isVerified) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.interestRate = interestRate;
        this.email = email;
        this.OTP = OTP;
        this.otpExpiredDate = otpExpiredDate;
        this.isVerified = isVerified;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return accountHolderName;
    }

    public void setName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
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
