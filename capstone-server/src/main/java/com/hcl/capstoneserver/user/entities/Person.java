package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserType;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person extends AppUser {
    private String name;
    private String address;
    @Column(unique = true)
    private String email;
    private String phone;
    private Float interestRate;

    public Person() {
        super();
    }

    public Person(
            String userId,
            String password,
            UserType userType,
            String name,
            String address,
            String email,
            String phone,
            Float interestRate
    ) {
        super(userId, password, userType);
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.interestRate = interestRate;
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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", interestRate=" + interestRate +
                '}';
    }
}
