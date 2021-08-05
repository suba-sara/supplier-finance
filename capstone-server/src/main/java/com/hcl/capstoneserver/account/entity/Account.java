package com.hcl.capstoneserver.account.entity;

import com.hcl.capstoneserver.AccountStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Account {
    @Id
    private Integer accountNumber;
    private String name;
    private String email;
    private Integer type;
    private Integer OTP;
    private Date otpExpiredDate;
    private AccountStatus accountStatus;

    public Account() {
    }

    public Account(Integer accountNumber, String name, String email, Integer type, Integer OTP, Date otpExpiredDate, AccountStatus accountStatus) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
        this.type = type;
        this.OTP = OTP;
        this.otpExpiredDate = otpExpiredDate;
        this.accountStatus = accountStatus;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
