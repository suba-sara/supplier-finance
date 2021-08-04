package com.hcl.capstoneserver.user.entities;

import com.hcl.capstoneserver.user.UserType;

import javax.persistence.Column;

public class Banker extends Person {
    @Column(unique = true)
    public String employeeId;

    public Banker() {
    }

    public Banker(
            String userId,
            String password,
            UserType userType,
            String name,
            String address,
            String email,
            String phone,
            Float interestRate,
            String employeeId
    ) {
        super(userId, password, userType, name, address, email, phone, interestRate);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
