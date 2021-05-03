package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Supplier extends Person {

    @Column(unique = true)
    private int supplierId;

    public Supplier() {
    }

    public Supplier(String userId, String password, String name, String address, String email, String phone, Float interestRate, int supplierId) {
        super(userId, password, UserType.SUPPLIER, name, address, email, phone, interestRate);
        this.supplierId = supplierId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                "} " + super.toString();
    }
}
