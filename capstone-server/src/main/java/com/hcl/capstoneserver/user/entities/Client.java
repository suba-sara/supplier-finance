package com.hcl.capstoneserver.user.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Client {
    @Id
    private int clientId;

    private String name;
    private String address;
    private String email;
    private String phone;
    private Float interestRate;
    private int accountNumber;

    @OneToOne
    private AppUser appUser;
}
