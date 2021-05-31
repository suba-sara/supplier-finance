package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserType;

import javax.persistence.Entity;

@Entity
public class Supplier extends Person {
    private String supplierId;

    public Supplier() {
        super();
    }


    public Supplier(
            String userId, String password, String name, String address, String email,
            String phone, Float interestRate, String supplierId
    ) {
        super(userId, password, UserType.SUPPLIER, name, address, email, phone, interestRate);
        this.supplierId = supplierId;
    }

    public Supplier(
            String userId, String password, String name, String address, String email,
            String phone, Float interestRate
    ) {
        super(userId, password, UserType.SUPPLIER, name, address, email, phone, interestRate);
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                "} " + super.toString();
    }
}
