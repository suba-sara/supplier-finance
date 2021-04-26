package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserRole;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Supplier extends AppUser{
    @Column(unique = true)
    private int supplierId;

    private String name;
    private String address;
    private String email;
    private String phone;
    private Float interestRate;

    @Override
    public UserRole getUserRole() {
        return UserRole.SUPPLIER;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
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
}
