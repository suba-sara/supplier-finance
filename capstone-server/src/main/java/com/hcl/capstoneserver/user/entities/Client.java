package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Client extends Person {

    @Column(unique = true)
    private String clientId;

    private Integer accountNumber;

    public Client() {
    }

    public Client(
            String userId,
            String password,
            String name,
            String address,
            String email,
            String phone,
            Float interestRate,
            int accountNumber
    ) {
        super(userId, password, UserType.CLIENT, name, address, email, phone, interestRate);
        this.accountNumber = accountNumber;
    }

    public Client(
            String userId,
            String password,
            String name,
            String address,
            String email,
            String phone,
            Float interestRate,
            String clientId,
            int accountNumber
    ) {
        super(userId, password, UserType.CLIENT, name, address, email, phone, interestRate);
        this.clientId = clientId;
        this.accountNumber = accountNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }
}
