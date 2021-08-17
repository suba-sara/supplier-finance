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
            String phone, String supplierId, String accountNumber
    ) {
        super(userId, password, UserType.SUPPLIER, name, address, email, phone, accountNumber);
        this.supplierId = supplierId;
    }

    public Supplier(
            String userId, String password, String name, String address, String email,
            String phone, String accountNumber
    ) {
        super(userId, password, UserType.SUPPLIER, name, address, email, phone, accountNumber);
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
