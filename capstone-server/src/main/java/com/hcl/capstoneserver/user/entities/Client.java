package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserRole;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Client extends AppUser{
    @Column(unique = true)
    private int clientId;

    private String name;
    private String address;
    private String email;
    private String phone;
    private Float interestRate;
    private int accountNumber;

    public Client() {
    }

    public Client(String userId, String password, int clientId, String name,
                  String address, String email, String phone, Float interestRate, int accountNumber) {
        super(userId, password, "CLIENT");
        this.clientId = clientId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.interestRate = interestRate;
        this.accountNumber = accountNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
