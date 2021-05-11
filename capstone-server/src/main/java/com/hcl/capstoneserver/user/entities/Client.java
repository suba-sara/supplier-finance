package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.generator.id.CustomIdGenerator;
import com.hcl.capstoneserver.user.UserType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Client extends Person {

    @Column(unique = true)
    private String clientId;

    private int accountNumber;

    public Client() {
    }

    public Client(String userId,
                  String password,
                  String name,
                  String address,
                  String email,
                  String phone,
                  Float interestRate,
                  String clientId,
                  int accountNumber) {
        super(userId, password, UserType.CLIENT, name, address, email, phone, interestRate);
        this.accountNumber = accountNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
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
