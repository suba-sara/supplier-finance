package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Client extends Person {

    @Column(unique = true)
    private String clientId;

    public Client() {
    }

    public Client(
            String userId,
            String password,
            String name,
            String address,
            String email,
            String phone,
            String accountNumber
    ) {
        super(userId, password, UserType.CLIENT, name, address, email, phone, accountNumber);
    }

    public Client(
            String userId,
            String password,
            String name,
            String address,
            String email,
            String phone,
            String clientId,
            String accountNumber
    ) {
        super(userId, password, UserType.CLIENT, name, address, email, phone, accountNumber);
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
