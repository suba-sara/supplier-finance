package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Client extends Person {
    @Column(unique = true)
    private int clientId;

    private int accountNumber;

    public Client() {
    }

    public Client(String userId, String password, UserType userType, String name, String address, String email, String phone, Float interestRate, int clientId, int accountNumber) {
        super(userId, password, userType, name, address, email, phone, interestRate);
        this.clientId = clientId;
        this.accountNumber = accountNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", accountNumber=" + accountNumber +
                "} " + super.toString();
    }
}
